package br.jus.stf.processamentoinicial.autuacao.interfaces.actions;

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
import org.activiti.engine.impl.util.json.JSONArray;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
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
 * Realiza os testes de integração do peticionamento usando o mecanismo de ações da plataforma STF Digital.
 * 
 * @author Anderson.Araujo
 * 
 * @version 1.0.0
 * 
 * @since 17.09.2015
 */
public class PeticionamentoActionIntegrationTests extends AbstractIntegrationTests {
	
	private String peticaoValidaParaAutuacao;
	private String peticaoAutuadaParaDistribuicao;
	private String peticaoEletronica;
	private String peticaoFisicaParaRegistro;
	private String peticaoFisicaParaPreautuacao;
	private String peticaoFisicaInvalidaParaPreautuacao;
	private String peticaoFisicaParaDevolucao;
	private String peticaoInvalidaParaAutuacao;
	private String tarefaParaAssumir;
	
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
		
		//Cria um objeto para ser usado no processo de rejeição de uma petição.
		StringBuilder peticaoInValidaParaAutuacao =  new StringBuilder();
		peticaoInValidaParaAutuacao.append("{\"resources\": ");
		peticaoInValidaParaAutuacao.append("[{\"peticaoId\": @,");
		peticaoInValidaParaAutuacao.append("\"classeId\":\"ADI\",");
		peticaoInValidaParaAutuacao.append("\"valida\":false,");
		peticaoInValidaParaAutuacao.append("\"motivo\":\"Petição inválida\"}]}");
		this.peticaoInvalidaParaAutuacao = peticaoInValidaParaAutuacao.toString();
		
		//Cria um objeto para ser usado no processo de autuação de uma petição válida.
		StringBuilder peticaoEletronicaValidaParaAutuacao =  new StringBuilder();
		peticaoEletronicaValidaParaAutuacao.append("{\"resources\": ");
		peticaoEletronicaValidaParaAutuacao.append("[{\"peticaoId\": @,");
		peticaoEletronicaValidaParaAutuacao.append("\"classeId\":\"ADI\",");
		peticaoEletronicaValidaParaAutuacao.append("\"valida\":true,");
		peticaoEletronicaValidaParaAutuacao.append("\"motivo\":\"\"}]}");
		this.peticaoValidaParaAutuacao = peticaoEletronicaValidaParaAutuacao.toString();
		
		//Cria um objeto para ser usado no processo de distribuição de uma petição.
		StringBuilder peticaoAutuadaParaDistribuicao =  new StringBuilder();
		peticaoAutuadaParaDistribuicao.append("{\"resources\": ");
		peticaoAutuadaParaDistribuicao.append("[{\"tipoDistribuicao\":\"PREVENCAO\",");
		peticaoAutuadaParaDistribuicao.append("\"peticaoId\": @,");
		peticaoAutuadaParaDistribuicao.append("\"justificativa\":\"Recurso do processo prevento.\",");
		peticaoAutuadaParaDistribuicao.append("\"processosPreventos\":[1]}]}");
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
		peticaoEletronica.append("{\"resources\": [{\"classeId\":\"HC\",");
		peticaoEletronica.append("\"partesPoloAtivo\":[1, 2],");
		peticaoEletronica.append("\"partesPoloPassivo\":[3, 4],");
		peticaoEletronica.append("\"pecas\": [{\"documentoTemporario\":\"" + idDoc + "\",");
		peticaoEletronica.append("\"tipo\":1}]}]}");
		this.peticaoEletronica = peticaoEletronica.toString();

		//Cria um objeto contendo os dados da petição física a ser usado no teste do registro da petição física.
		StringBuilder peticaoFisica =  new StringBuilder();
		peticaoFisica.append("{\"resources\": [{\"formaRecebimento\":\"SEDEX\",");
		peticaoFisica.append("\"quantidadeVolumes\":2,");
		peticaoFisica.append("\"quantidadeApensos\":1,");
		peticaoFisica.append("\"numeroSedex\":\"SR123456789BR\",");
		peticaoFisica.append("\"tipoProcesso\":\"ORIGINARIO\"}]}");
		this.peticaoFisicaParaRegistro = peticaoFisica.toString();
		
		//Cria um objeto contendo os dados de uma petição física a ser usado no processo de préautuação.
		StringBuilder peticaoFisicaParaPreautuacao =  new StringBuilder();
		peticaoFisicaParaPreautuacao.append("{\"resources\": ");
		peticaoFisicaParaPreautuacao.append("[{\"peticaoId\": @,");
		peticaoFisicaParaPreautuacao.append("\"classeId\":\"ADI\",");
		peticaoFisicaParaPreautuacao.append("\"valida\":true}]}");
		this.peticaoFisicaParaPreautuacao = peticaoFisicaParaPreautuacao.toString();
		
		//Cria um objeto contendo os dados de uma petição física a ser usado no processo de préautuação.
		StringBuilder peticaoFisicaInvalidaParaPreautuacao =  new StringBuilder();
		peticaoFisicaInvalidaParaPreautuacao.append("{\"resources\": ");
		peticaoFisicaInvalidaParaPreautuacao.append("[{\"peticaoId\": @,");
		peticaoFisicaInvalidaParaPreautuacao.append("\"classeId\":\"ADI\",");
		peticaoFisicaInvalidaParaPreautuacao.append("\"motivo\":\"Teste\",");
		peticaoFisicaInvalidaParaPreautuacao.append("\"valida\":false}]}");
		this.peticaoFisicaInvalidaParaPreautuacao = peticaoFisicaInvalidaParaPreautuacao.toString();
		
		//Cria um objeto para ser usado no processo de devolução de uma petição física.
		StringBuilder peticaoFisicaParaDevolucao =  new StringBuilder();
		peticaoFisicaParaDevolucao.append("{\"resources\": ");
		peticaoFisicaParaDevolucao.append("[{\"peticaoId\": @,");		
		peticaoFisicaParaDevolucao.append("\"numeroOficio\":1234,");
		peticaoFisicaParaDevolucao.append("\"tipoDevolucao\":\"REMESSA_INDEVIDA\"}]}");
		this.peticaoFisicaParaDevolucao = peticaoFisicaParaDevolucao.toString();
		
		this.prepareCommand = "{\"certificateAsHex\":\"@\"}";
		this.provideToSignCommand = "{\"documentId\":@documentId,\"signerId\":\"@signerId\"}";
		this.preSignCommand = "{\"signerId\":\"@\"}";
		this.postSignCommand = "{\"signatureAsHex\":\"@signatureAsHex\",\"signerId\":\"@signerId\"}";
		this.assinarDevolucaoPeticaoCommand = "{\"resources\":[{\"peticaoId\":@peticaoId,\"documentoId\":\"@documentoId\"}]}";
		
		this.tarefaParaAssumir = "{\"resources\": [{\"tarefaId\": @}]}";
	}
	
	@Test
    public void executarAcaoDistribuirPeticaoEletronica() throws Exception {
    	
    	String peticaoId = "";
    	String tarefaObject = "";
    	
    	//Envia a petição eletrônica.
    	peticaoId = super.mockMvc.perform(post("/api/actions/registrar-peticao-eletronica/execute").header("login", "peticionador").contentType(MediaType.APPLICATION_JSON)
    		.content(this.peticaoEletronica)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		//Recupera a(s) tarefa(s) do autuador.
		tarefaObject = super.mockMvc.perform(get("/api/workflow/tarefas/papeis").header("login", "autuador")).andExpect(status().isOk())
			.andExpect(jsonPath("$[0].nome", is("autuar"))).andReturn().getResponse().getContentAsString();
		
		assumirTarefa(tarefaObject);
		
		//Realiza a autuação.
		super.mockMvc.perform(post("/api/actions/autuar/execute").contentType(MediaType.APPLICATION_JSON)
			.content(this.peticaoValidaParaAutuacao.replace("@", peticaoId))).andExpect(status().isOk());
		
		//Recupera a(s) tarefa(s) do distribuidor.
		tarefaObject = super.mockMvc.perform(get("/api/workflow/tarefas/papeis").header("login", "distribuidor")).andExpect(status().isOk())
			.andExpect(jsonPath("$[0].nome", is("distribuir-processo"))).andReturn().getResponse().getContentAsString();
		
		assumirTarefa(tarefaObject);
		
		//Realiza a distribuição.
		super.mockMvc.perform(post("/api/actions/distribuir-processo/execute").header("login", "distribuidor").contentType(MediaType.APPLICATION_JSON)
			.content(this.peticaoAutuadaParaDistribuicao.replace("@", peticaoId))).andExpect(status().isOk()).andExpect(jsonPath("$.relator", is(28)));
		
    }
	
    @Test
    public void executarAcaoRegistroPeticaoFisica() throws Exception {
    	
    	String peticaoId = "";
    	String tarefaObject = "";
    	
    	//Envia a petição eletrônica.
    	peticaoId = super.mockMvc.perform(post("/api/actions/registrar-peticao-fisica/execute").header("login", "recebedor").contentType(MediaType.APPLICATION_JSON)
    		.content(this.peticaoFisicaParaRegistro)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
    	//Recupera a(s) tarefa(s) do préautuador.
    	tarefaObject = super.mockMvc.perform(get("/api/workflow/tarefas/papeis").header("login", "preautuador-originario")).andExpect(status().isOk())
			.andExpect(jsonPath("$[0].nome", is("preautuar"))).andReturn().getResponse().getContentAsString();
    	
    	//Assumir a(s) tarefa(s) do préautuador.
    	assumirTarefa(tarefaObject);
    	    	
		//Realiza a préautuação da petição física.
		super.mockMvc.perform(post("/api/actions/preautuar/execute").contentType(MediaType.APPLICATION_JSON)
	    		.content(this.peticaoFisicaParaPreautuacao.replace("@", peticaoId))).andExpect(status().isOk());
		
		//Recupera a(s) tarefa(s) do autuador.
		tarefaObject = super.mockMvc.perform(get("/api/workflow/tarefas/papeis").header("login", "autuador")).andExpect(status().isOk())
			.andExpect(jsonPath("$[0].nome", is("autuar"))).andReturn().getResponse().getContentAsString();
		
    	//Assumir a(s) tarefa(s) do autuador.
		assumirTarefa(tarefaObject);
		
		//Realiza a autuação.
		super.mockMvc.perform(post("/api/actions/autuar/execute").contentType(MediaType.APPLICATION_JSON)
			.content(this.peticaoValidaParaAutuacao.replace("@", peticaoId))).andExpect(status().isOk());
		
		//Recupera a(s) tarefa(s) do distribuidor.
		tarefaObject = super.mockMvc.perform(get("/api/workflow/tarefas/papeis").header("login", "distribuidor")).andExpect(status().isOk())
			.andExpect(jsonPath("$[0].nome", is("distribuir-processo"))).andReturn().getResponse().getContentAsString();
		
    	//Assumir a(s) tarefa(s) do distribuidor.
		assumirTarefa(tarefaObject);
		
		//Realiza a distribuição.
		super.mockMvc.perform(post("/api/actions/distribuir-processo/execute").contentType(MediaType.APPLICATION_JSON)
			.content(this.peticaoAutuadaParaDistribuicao.replace("@", peticaoId))).andExpect(status().isOk()).andExpect(jsonPath("$.relator", is(28)));

    }
    
    @Test
    public void executarAcaoRejeitarPeticao() throws Exception {
    	
    	String peticaoId = "";
    	String tarefaObject = "";
    	
    	//Envia a petição eletrônica.
    	peticaoId = super.mockMvc.perform(post("/api/actions/registrar-peticao-eletronica/execute").header("login", "peticionador").contentType(MediaType.APPLICATION_JSON)
    		.content(this.peticaoEletronica)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		//Recupera a(s) tarefa(s) do autuador.
    	tarefaObject = super.mockMvc.perform(get("/api/workflow/tarefas/papeis").header("login", "autuador")).andExpect(status().isOk())
			.andExpect(jsonPath("$[0].nome", is("autuar"))).andReturn().getResponse().getContentAsString();
		
		assumirTarefa(tarefaObject);
		
		//Realiza a autuação.
		super.mockMvc.perform(post("/api/actions/autuar/execute").contentType(MediaType.APPLICATION_JSON)
			.content(this.peticaoInvalidaParaAutuacao.replace("@", peticaoId))).andExpect(status().isOk());
    }
    
    @Test
    public void devolverPeticaoFisica() throws Exception {
    	
    	String peticaoId = "";
    	String tarefaObject = "";
    	
    	//Envia a petição eletrônica.
    	peticaoId = super.mockMvc.perform(post("/api/actions/registrar-peticao-fisica/execute").header("login", "recebedor").contentType(MediaType.APPLICATION_JSON)
    		.content(this.peticaoFisicaParaRegistro)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
    	//Recupera a(s) tarefa(s) do préautuador.
    	tarefaObject = super.mockMvc.perform(get("/api/workflow/tarefas/papeis").header("login", "preautuador-originario")).andExpect(status().isOk())
			.andExpect(jsonPath("$[0].nome", is("preautuar"))).andReturn().getResponse().getContentAsString();
    	
    	//Assumir a(s) tarefa(s) do préautuador.
    	assumirTarefa(tarefaObject);
    	    	
		//Realiza a préautuação da petição física.
		super.mockMvc.perform(post("/api/actions/preautuar/execute").contentType(MediaType.APPLICATION_JSON)
	    		.content(this.peticaoFisicaInvalidaParaPreautuacao.replace("@", peticaoId))).andExpect(status().isOk());
		
		//Recuperar as tarefas de cartoraria.
		tarefaObject = this.mockMvc.perform(get("/api/workflow/tarefas/papeis").header("login", "cartoraria")).andExpect(status().isOk())
			.andExpect(jsonPath("$[0].nome", is("devolver-peticao"))).andReturn().getResponse().getContentAsString();
		
    	assumirTarefa(tarefaObject);
		
		//Devolve a petição.
		this.mockMvc.perform(post("/api/actions/devolver-peticao/execute").contentType(MediaType.APPLICATION_JSON)
				.content(this.peticaoFisicaParaDevolucao.replace("@", peticaoId))).andExpect(status().isOk());
		
		//Tenta recuperar as tarefas do cartoraria.
		tarefaObject = this.mockMvc.perform(get("/api/workflow/tarefas/papeis").header("login", "gestor-recebimento")).andExpect(status().isOk())
			.andExpect(jsonPath("$[0].nome", is("assinar-devolucao-peticao"))).andReturn().getResponse().getContentAsString();
		
		assumirTarefa(tarefaObject);
		
		// Realiza a assinatura do documento de devolução e finalizando, portanto, a devolução.
		assinarDevolucaoPeticao(peticaoId);
    }
    
    private void assumirTarefa(String tarefaObject) throws Exception {
    	String tarefaId = getTarefaId(tarefaObject);
		super.mockMvc.perform(post("/api/actions/assumir-tarefa/execute").contentType(MediaType.APPLICATION_JSON)
	    		.content(this.tarefaParaAssumir.replace("@", tarefaId))).andExpect(status().isOk());
    }
    
    private String getTarefaId(String json) {
    	return ((Integer) new JSONArray(json).getJSONObject(0).get("id")).toString();
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
}
