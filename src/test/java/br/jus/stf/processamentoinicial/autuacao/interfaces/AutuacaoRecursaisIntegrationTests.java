package br.jus.stf.processamentoinicial.autuacao.interfaces;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.CertificateEncodingException;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MvcResult;

import com.jayway.jsonpath.JsonPath;

import br.jus.stf.plataforma.shared.certification.infra.pki.CustomKeyStore;
import br.jus.stf.plataforma.shared.certification.support.pki.PlataformaUnitTestingUser;
import br.jus.stf.plataforma.shared.indexacao.IndexadorRestAdapter;
import br.jus.stf.plataforma.shared.tests.AbstractIntegrationTests;
import br.jus.stf.processamentoinicial.autuacao.infra.eventbus.PeticaoIndexadorConsumer;
import br.jus.stf.processamentoinicial.autuacao.infra.eventbus.PeticaoStatusIndexadorConsumer;
import br.jus.stf.processamentoinicial.recursaledistribuicao.infra.eventbus.ProcessoDistribuidoIndexadorConsumer;

/**
 * @author Rafael Alencar
 * 
 * @since 14.01.2016
 */
public class AutuacaoRecursaisIntegrationTests extends AbstractIntegrationTests {
	
	private String peticaoValidaParaAutuacao;
	private String peticaoAutuadaParaDistribuicao;
	private String peticaoEletronica;
	private String peticaoFisicaParaRegistro;
	private String peticaoFisicaParaPreautuacao;
	private String peticaoFisicaInvalidaParaPreautuacao;
	private String peticaoInvalidaParaAutuacao;
	private String peticaoFisicaParaDevolucao;
	
	private String prepareCommand;
	private String provideToSignCommand;
	private String preSignCommand;
	private String postSignCommand;
	private String assinarDevolucaoPeticaoCommand;
	
	@Spy
	private IndexadorRestAdapter indexadorRestAdapter;
	
	@Autowired
	@InjectMocks
	private ProcessoDistribuidoIndexadorConsumer processoIndexadorConsumer;
	
	@Autowired
	@InjectMocks
	private PeticaoIndexadorConsumer peticaoIndexadorConsumer;
	
	@Autowired
	@InjectMocks
	private PeticaoStatusIndexadorConsumer peticaoStatusIndexadorConsumer;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		doNothing().when(indexadorRestAdapter).indexar(any(), any());
		doNothing().when(indexadorRestAdapter).atualizarItemDeColecao(any(), any(), any(), any(), any(), any(), any());
	}
	
	@Before
	public void criarObjetosJSON() throws UnsupportedEncodingException, Exception{
		//Cria um objeto para ser usado no processo de autuação de uma petição válida.
		StringBuilder peticaoEletronicaValidaParaAutuacao =  new StringBuilder();
		peticaoEletronicaValidaParaAutuacao.append("{\"classeId\":\"ADI\",");
		peticaoEletronicaValidaParaAutuacao.append("\"valida\":true,");
		peticaoEletronicaValidaParaAutuacao.append("\"motivo\":\"\"}");
		this.peticaoValidaParaAutuacao = peticaoEletronicaValidaParaAutuacao.toString();
		
		//Cria um objeto para ser usado no processo de distribuição de uma petição.
		StringBuilder peticaoAutuadaParaDistribuicao =  new StringBuilder();
		peticaoAutuadaParaDistribuicao.append("{\"tipoDistribuicao\":\"COMUM\",");
		peticaoAutuadaParaDistribuicao.append("\"peticaoId\": @,");
		peticaoAutuadaParaDistribuicao.append("\"ministrosCandidatos\":[36],");
		peticaoAutuadaParaDistribuicao.append("\"ministrosImpedidos\":[46,47,1,42,28,44,49,45,30,48]}");
		this.peticaoAutuadaParaDistribuicao = peticaoAutuadaParaDistribuicao.toString();
		
		//Envia um documento para que seja obtido o seu ID. Este será usado para simular o teste de envio de uma petição eletrônica.
		
		String idDoc = "";
		String nomeArquivo = "teste_arq_temp.pdf";
		String mime = "application/pdf";
		String caminho = "certification/pdf-de-teste-assinado-02.pdf";
		
		byte[] arquivo = IOUtils.toByteArray(new ClassPathResource(caminho).getInputStream());

	    MockMultipartFile mockArquivo = new MockMultipartFile("file", nomeArquivo, mime, arquivo);
		
	    //Envia um documento antes de enviar a petição.
	    idDoc = mockMvc.perform(fileUpload("/api/documentos/upload/assinado").file(mockArquivo).contentType(MediaType.MULTIPART_FORM_DATA).content(arquivo))
	    	.andExpect(status().is2xxSuccessful()).andReturn().getResponse().getContentAsString();
		
	    //Cria um objeto contendo os dados da petição eletrônica a ser usado no teste.
	    StringBuilder peticaoEletronica =  new StringBuilder();
		peticaoEletronica.append("{\"classeId\":\"HC\",");
		peticaoEletronica.append("\"partesPoloAtivo\":[1, 2],");
		peticaoEletronica.append("\"partesPoloPassivo\":[3, 4],");
		peticaoEletronica.append("\"pecas\": [{\"documentoTemporario\":\"" + idDoc + "\",");
		peticaoEletronica.append("\"tipo\":1}]}]}");
		this.peticaoEletronica = peticaoEletronica.toString();
		
		//Cria um objeto contendo os dados da petição física a ser usado no teste do registro da petição física.
		StringBuilder peticaoFisica =  new StringBuilder();
		peticaoFisica.append("{\"formaRecebimento\":\"SEDEX\",");
		peticaoFisica.append("\"quantidadeVolumes\":2,");
		peticaoFisica.append("\"quantidadeApensos\":1,");
		peticaoFisica.append("\"tipoProcesso\":\"RECURSAL\",");
		peticaoFisica.append("\"numeroSedex\":\"SR123456789BR\"}");
		this.peticaoFisicaParaRegistro = peticaoFisica.toString();
		
		//Cria um objeto contendo os dados de uma petição física a ser usado no processo de préautuação.
		StringBuilder peticaoFisicaParaPreautuacao =  new StringBuilder();
		peticaoFisicaParaPreautuacao.append("{\"classeId\":\"ADI\",");
		peticaoFisicaParaPreautuacao.append("\"valida\":true,");
		peticaoFisicaParaPreautuacao.append("\"motivo\":\"\"}");
		this.peticaoFisicaParaPreautuacao = peticaoFisicaParaPreautuacao.toString();
		
		//Cria um objeto contendo os dados de uma petição física a ser devolvida.
		StringBuilder peticaoFisicaInvalidaParaPreautuacao =  new StringBuilder();
		peticaoFisicaInvalidaParaPreautuacao.append("{\"classeId\":\"ADI\",");
		peticaoFisicaInvalidaParaPreautuacao.append("\"valida\":false,");
		peticaoFisicaInvalidaParaPreautuacao.append("\"motivo\":\"Pq sim\"}");
		this.peticaoFisicaInvalidaParaPreautuacao = peticaoFisicaInvalidaParaPreautuacao.toString();
		
		//Cria um objeto para ser usado no processo de rejeição de uma petição.
		StringBuilder peticaoInValidaParaAutuacao =  new StringBuilder();
		peticaoInValidaParaAutuacao.append("{\"classeId\":\"ADI\",");
		peticaoInValidaParaAutuacao.append("\"valida\":false,");
		peticaoInValidaParaAutuacao.append("\"motivo\":\"Petição inválida\"}");
		this.peticaoInvalidaParaAutuacao = peticaoInValidaParaAutuacao.toString();
		
		//Cria um objeto para ser usado no processo de devolução de uma petição física.
		StringBuilder peticaoFisicaParaDevolucao =  new StringBuilder();
		peticaoFisicaParaDevolucao.append("{\"numeroOficio\":1234,");
		peticaoFisicaParaDevolucao.append("\"tipoDevolucao\":\"REMESSA_INDEVIDA\"}");
		this.peticaoFisicaParaDevolucao = peticaoFisicaParaDevolucao.toString();
		
		//Cria os objetos a serem usados no processo de assinatura do documento de devolução.
		this.prepareCommand = "{\"certificateAsHex\":\"@\"}";
		this.provideToSignCommand = "{\"documentId\":@documentId,\"signerId\":\"@signerId\"}";
		this.preSignCommand = "{\"signerId\":\"@\"}";
		this.postSignCommand = "{\"signatureAsHex\":\"@signatureAsHex\",\"signerId\":\"@signerId\"}";
		this.assinarDevolucaoPeticaoCommand = "{\"resources\":[{\"peticaoId\":@peticaoId,\"documentoId\":\"@documentoId\"}]}";
	}
	
	@Test
	@Ignore // TODO Implementar no teste de integração usando o mecanismo de ação
	public void distribuirPeticaoEletronica() throws Exception {
		String peticaoId = "";
				
		//Envia a petição eletrônica
		peticaoId = this.mockMvc.perform(post("/api/peticoes/").header("login", "peticionador").contentType(MediaType.APPLICATION_JSON)
			.content(this.peticaoEletronica)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		//Recupera a(s) tarefa(s) do autuador.
		this.mockMvc.perform(get("/api/workflow/tarefas").header("login", "autuador")).andExpect(status().isOk())
			.andExpect(jsonPath("$[0].nome", is("autuar")));
		
		//Realiza a autuação.
		this.mockMvc.perform(post("/api/peticoes/" + peticaoId + "/autuar").contentType(MediaType.APPLICATION_JSON)
			.content(this.peticaoValidaParaAutuacao)).andExpect(status().isOk());
		
		//Recupera a(s) tarefa(s) do distribuidor.
		this.mockMvc.perform(get("/api/workflow/tarefas").header("login", "distribuidor")).andExpect(status().isOk())
			.andExpect(jsonPath("$[0].nome", is("distribuir-processo")));
		
		//Realiza a distribuição.
		this.mockMvc.perform(post("/api/peticoes/" + peticaoId + "/distribuir").header("login", "distribuidor").contentType(MediaType.APPLICATION_JSON)
			.content(this.peticaoAutuadaParaDistribuicao.replace("@", peticaoId))).andExpect(status().isOk()).andExpect(jsonPath("$.relator", is(36)));
		
		//Tenta recuperar as tarefas do autuador. A ideia é receber uma lista vazia, já que a instância do processo foi encerrada.
		this.mockMvc.perform(get("/api/workflow/tarefas").header("login", "autuador")).andExpect(status().isOk())
			.andExpect(jsonPath("$").doesNotExist());
		
		//Recupera as partes da petição.
		this.mockMvc.perform(get("/api/peticoes/" + peticaoId + "/partes").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk()).andExpect(jsonPath("$.PoloAtivo").isArray());
	}
	
	@Test
	@Ignore // TODO Implementar no teste de integração usando o mecanismo de ação
	public void distribuirPeticaoFisica() throws Exception {
		
		String peticaoId = "";
		
		//Registra a petição física.
		peticaoId = this.mockMvc.perform(post("/api/peticoes/fisicas").header("login", "recebedor").contentType(MediaType.APPLICATION_JSON)
			.content(peticaoFisicaParaRegistro.toString())).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		//Recupera a(s) tarefa(s) do préautuador.
		this.mockMvc.perform(get("/api/workflow/tarefas").header("login", "preautuador")).andExpect(status().isOk())
			.andExpect(jsonPath("$[0].nome", is("preautuar")));
		
		//Faz a préautuação da petição registrada.
		this.mockMvc.perform(post("/api/peticoes/fisicas/" + peticaoId + "/preautuar").contentType(MediaType.APPLICATION_JSON)
				.content(peticaoFisicaParaPreautuacao.toString())).andExpect(status().isOk());
		
		//Recupera a(s) tarefa(s) do autuador.
		this.mockMvc.perform(get("/api/workflow/tarefas").header("login", "autuador")).andExpect(status().isOk())
			.andExpect(jsonPath("$[0].nome", is("autuar")));
		
		//Realiza a autuação da petição préautuada.
		this.mockMvc.perform(post("/api/peticoes/" + peticaoId + "/autuar").contentType(MediaType.APPLICATION_JSON)
				.content(this.peticaoValidaParaAutuacao)).andExpect(status().isOk());
		
		//Recupera a(s) tarefa(s) do distribuidor.
		this.mockMvc.perform(get("/api/workflow/tarefas").header("login", "distribuidor")).andExpect(status().isOk())
			.andExpect(jsonPath("$[0].nome", is("distribuir-processo")));
		
		//Realiza a distribuição.
		this.mockMvc.perform(post("/api/peticoes/" + peticaoId + "/distribuir").contentType(MediaType.APPLICATION_JSON)
			.content(this.peticaoAutuadaParaDistribuicao.replace("@", peticaoId))).andExpect(status().isOk()).andExpect(jsonPath("$.relator", is(36)));
		
		//Tenta recuperar as tarefas do autuador. A ideia é receber uma lista vazia, já que a instância do processo foi encerrada.
		this.mockMvc.perform(get("/api/workflow/tarefas").header("login", "autuador")).andExpect(status().isOk())
			.andExpect(jsonPath("$").doesNotExist());
	}
	
	@Test
	@Ignore // TODO Implementar no teste de integração usando o mecanismo de ação
	public void rejeitarPeticao() throws Exception{
		
		String peticaoId = "";
		
		//Envia a petição eletrônica
		peticaoId = this.mockMvc.perform(post("/api/peticoes/").header("login", "peticionador").contentType(MediaType.APPLICATION_JSON)
			.content(this.peticaoEletronica)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		//Recupera a(s) tarefa(s) do autuador.
		this.mockMvc.perform(get("/api/workflow/tarefas").header("login", "autuador")).andExpect(status().isOk())
			.andExpect(jsonPath("$[0].nome", is("autuar")));
		
		//Realiza a autuação.
		this.mockMvc.perform(post("/api/peticoes/" + peticaoId + "/autuar").contentType(MediaType.APPLICATION_JSON)
			.content(this.peticaoInvalidaParaAutuacao)).andExpect(status().isOk());
		
		//Tenta recuperar as tarefas do cartoraria. A ideia é receber uma lista vazia, já que a instância do processo foi encerrada.
		this.mockMvc.perform(get("/api/workflow/tarefas").header("login", "cartoraria")).andExpect(status().isOk())
			.andExpect((jsonPath("$", hasSize(0))));
	}
	
	@Test
	@Ignore // TODO Implementar no teste de integração usando o mecanismo de ação
	public void devolverPeticao() throws Exception{
		
		String peticaoId = "";
		
		//Registra a petição física.
		peticaoId = this.mockMvc.perform(post("/api/peticoes/fisicas").header("login", "recebedor").contentType(MediaType.APPLICATION_JSON)
			.content(peticaoFisicaParaRegistro.toString())).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		//Recupera a(s) tarefa(s) do préautuador.
		this.mockMvc.perform(get("/api/workflow/tarefas").header("login", "preautuador")).andExpect(status().isOk())
			.andExpect(jsonPath("$[0].nome", is("preautuar")));
		
		//Faz a préautuação da petição registrada.
		this.mockMvc.perform(post("/api/peticoes/fisicas/" + peticaoId + "/preautuar").contentType(MediaType.APPLICATION_JSON)
				.content(this.peticaoFisicaInvalidaParaPreautuacao.toString())).andExpect(status().isOk());
		
		//Tenta recuperar as tarefas do autuador. A ideia é receber uma lista vazia, já que a instância do processo foi encerrada.
		this.mockMvc.perform(get("/api/workflow/tarefas").header("login", "cartoraria")).andExpect(status().isOk())
			.andExpect(jsonPath("$[0].nome", is("devolver-peticao")));
		
		//Devolve a petição.
		this.mockMvc.perform(post("/api/peticoes/" + peticaoId + "/devolver").contentType(MediaType.APPLICATION_JSON)
				.content(this.peticaoFisicaParaDevolucao.toString())).andExpect(status().isOk());
		
		//Tenta recuperar as tarefas do cartoraria. A ideia é receber uma lista vazia, já que a instância do processo foi encerrada.
		this.mockMvc.perform(get("/api/workflow/tarefas").header("login", "cartoraria")).andExpect(status().isOk())
			.andExpect((jsonPath("$", hasSize(0))));
		
		// Realiza a assinatura do documento de devolução e finalizando, portanto, a devolução.
		assinarDevolucaoPeticao(peticaoId);
	}

	private void assinarDevolucaoPeticao(String peticaoId)
			throws UnsupportedEncodingException, Exception, CertificateEncodingException {
		String peticaoJson = this.mockMvc.perform(get("/api/peticoes/" + peticaoId).header("login", "gestor-recebimento"))
			.andExpect(status().isOk()).andExpect(jsonPath("$.pecas[?(@.tipoId == 8)]", hasSize(1))).andReturn().getResponse().getContentAsString();
		Integer documentoId = JsonPath.read(peticaoJson, "$.pecas[?(@.tipoId == 8)].documentoId[0]");
		
		CustomKeyStore userStore = PlataformaUnitTestingUser.instance().userStore();
		String hexCertificate = Hex.encodeHexString(userStore.certificate().getEncoded());
		
		MvcResult result = this.mockMvc.perform(post("/api/certification/signature/prepare").header("login", "gestor-recebimento")
			.contentType(MediaType.APPLICATION_JSON).content(this.prepareCommand.replace("@", hexCertificate)))
			.andExpect(status().isOk()).andExpect(jsonPath("$.signerId").exists()).andReturn();
		MockHttpSession signatureSession = (MockHttpSession) result.getRequest().getSession();
		String signerJson = result.getResponse().getContentAsString();
		String signerId = JsonPath.read(signerJson, "$.signerId");
		
		this.mockMvc.perform(post("/api/certification/signature/provide-to-sign").session(signatureSession).header("login", "gestor-recebimento")
			.contentType(MediaType.APPLICATION_JSON).content(this.provideToSignCommand.replace("@documentId", documentoId.toString())
				.replace("@signerId", signerId))).andExpect(status().isOk());
		
		String preSignJson = this.mockMvc.perform(post("/api/certification/signature/pre-sign").session(signatureSession).header("login", "gestor-recebimento")
			.contentType(MediaType.APPLICATION_JSON).content(this.preSignCommand.replace("@", signerId)))
			.andExpect(status().isOk()).andExpect(jsonPath("$.hash").exists()).andExpect(jsonPath("$.hashType").exists())
			.andReturn().getResponse().getContentAsString();
		
		String data = JsonPath.read(preSignJson, "$.data");
		String signature = sign(data, userStore.keyPair().getPrivate());
		this.mockMvc.perform(post("/api/certification/signature/post-sign").session(signatureSession).header("login", "gestor-recebimento")
				.contentType(MediaType.APPLICATION_JSON).content(this.postSignCommand.replace("@signatureAsHex", signature)
					.replace("@signerId", signerId))).andExpect(status().isOk());
		
		String documentJson = this.mockMvc.perform(post("/api/certification/signature/save-signed/" + signerId).session(signatureSession).header("login", "gestor-recebimento"))
			.andExpect(status().isOk()).andExpect(jsonPath("$.documentId").exists()).andReturn().getResponse().getContentAsString();
		String signedDocumentId = JsonPath.read(documentJson, "$.documentId");
		
		this.mockMvc.perform(post("/api/actions/assinar-devolucao-peticao/execute").header("login", "gestor-recebimento")
				.contentType(MediaType.APPLICATION_JSON).content(this.assinarDevolucaoPeticaoCommand.replace("@peticaoId", peticaoId)
					.replace("@documentoId", signedDocumentId))).andExpect(status().isOk());
		
		this.mockMvc.perform(get("/api/workflow/tarefas").header("login", "gestor-recebimento")).andExpect(status().isOk())
			.andExpect((jsonPath("$", hasSize(0))));
	}
	
	private String sign(String dataAsHash, PrivateKey key) throws Exception {
		Signature signature = Signature.getInstance("SHA256withRSA");
		signature.initSign(key);
		signature.update(Hex.decodeHex(dataAsHash.toCharArray()));

		byte[] signed = signature.sign();

		return Hex.encodeHexString(signed);
	}
	
	@Test
	public void recuperarListaStatusPeticao() throws Exception {
		//Recupera as informações do usuário.
		this.mockMvc.perform(get("/api/peticoes/status")
			.header("login", "autuador")).andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(9)));
	}
}
