package br.jus.stf.processamentoinicial.autuacao.interfaces.actions;

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;

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
import org.springframework.mock.web.MockMultipartFile;

import br.jus.stf.plataforma.shared.indexacao.IndexadorRestAdapter;
import br.jus.stf.plataforma.shared.tests.AbstractIntegrationTests;
import br.jus.stf.processamentoinicial.autuacao.infra.eventbus.PeticaoIndexadorConsumer;
import br.jus.stf.processamentoinicial.distribuicao.infra.eventbus.ProcessoIndexadorConsumer;

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
	private String peticaoInvalidaParaAutuacao;
	private String tarefaParaAssumir;
	
	@Spy
	private IndexadorRestAdapter indexadorRestAdapter;
	
	@Autowired
	@InjectMocks
	private ProcessoIndexadorConsumer processoIndexadorConsumer;
	
	@Autowired
	@InjectMocks
	private PeticaoIndexadorConsumer peticaoIndexadorConsumer;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		doNothing().when(indexadorRestAdapter).indexar(any(), any());
	}
	
	@Before
	public void criarObjetosJSON() throws UnsupportedEncodingException, Exception{
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
		peticaoFisica.append("\"numeroSedex\":\"SR123456789BR\"}]}");
		this.peticaoFisicaParaRegistro = peticaoFisica.toString();
		
		//Cria um objeto contendo os dados de uma petição física a ser usado no processo de préautuação.
		StringBuilder peticaoFisicaParaPreautuacao =  new StringBuilder();
		peticaoFisicaParaPreautuacao.append("{\"resources\": ");
		peticaoFisicaParaPreautuacao.append("[{\"peticaoId\": @,");
		peticaoFisicaParaPreautuacao.append("\"classeId\":\"ADI\",");
		peticaoFisicaParaPreautuacao.append("\"valida\":true}]}");
		this.peticaoFisicaParaPreautuacao = peticaoFisicaParaPreautuacao.toString();
		
		//Cria um objeto para ser usado no processo de rejeição de uma petição.
		StringBuilder peticaoInValidaParaAutuacao =  new StringBuilder();
		peticaoInValidaParaAutuacao.append("{\"resources\": ");
		peticaoInValidaParaAutuacao.append("[{\"peticaoId\": @,");
		peticaoInValidaParaAutuacao.append("\"classeId\":\"ADI\",");
		peticaoInValidaParaAutuacao.append("\"valida\":false,");
		peticaoInValidaParaAutuacao.append("\"motivo\":\"Petição inválida\"}]}");
		this.peticaoInvalidaParaAutuacao = peticaoInValidaParaAutuacao.toString();
		
		StringBuilder tarefaParaAssumir = new StringBuilder();
		tarefaParaAssumir.append("{\"tarefaId\": @");
		this.tarefaParaAssumir = tarefaParaAssumir.toString();
	}
	
	@Test
    public void executarAcaoDistribuirPeticaoEletronica() throws Exception {
    	
    	String peticaoId = "";
    	
    	//Envia a petição eletrônica.
    	peticaoId = super.mockMvc.perform(post("/api/actions/registrar-peticao-eletronica/execute").header("login", "peticionador").contentType(MediaType.APPLICATION_JSON)
    		.content(this.peticaoEletronica)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		//Recupera a(s) tarefa(s) do autuador.
		super.mockMvc.perform(get("/api/workflow/tarefas").header("login", "autuador")).andExpect(status().isOk())
			.andExpect(jsonPath("$[0].nome", is("autuar")));
		
		//Realiza a autuação.
		super.mockMvc.perform(post("/api/actions/autuar/execute").contentType(MediaType.APPLICATION_JSON)
			.content(this.peticaoValidaParaAutuacao.replace("@", peticaoId))).andExpect(status().isOk());
		
		//Recupera a(s) tarefa(s) do distribuidor.
		super.mockMvc.perform(get("/api/workflow/tarefas").header("login", "distribuidor")).andExpect(status().isOk())
			.andExpect(jsonPath("$[0].nome", is("distribuir-processo")));
		
		//Realiza a distribuição.
		super.mockMvc.perform(post("/api/actions/distribuir-processo/execute").header("login", "distribuidor").contentType(MediaType.APPLICATION_JSON)
			.content(this.peticaoAutuadaParaDistribuicao.replace("@", peticaoId))).andExpect(status().isOk()).andExpect(jsonPath("$.relator", is(28)));
		
    }
	
    @Test
    public void executarAcaoRegistroPeticaoFisica() throws Exception {
    	
    	String peticaoId = "";
    	String tarefaObject = "";
    	String tarefaId = "";
    	
    	//Envia a petição eletrônica.
    	peticaoId = super.mockMvc.perform(post("/api/actions/registrar-peticao-fisica/execute").header("login", "recebedor").contentType(MediaType.APPLICATION_JSON)
    		.content(this.peticaoFisicaParaRegistro)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
    	//Recupera a(s) tarefa(s) do préautuador.
    	tarefaObject = super.mockMvc.perform(get("/api/workflow/tarefas/papeis").header("login", "preautuador-originario")).andExpect(status().isOk())
			.andExpect(jsonPath("$[0].nome", is("preautuar"))).andReturn().getResponse().getContentAsString();
    	tarefaId = getTarefaId(tarefaObject);
		
    	//Assumir a(s) tarefa(s) do préautuador.
		super.mockMvc.perform(get("/api/actions/assumir-tarefa/execute").contentType(MediaType.APPLICATION_JSON)
	    		.content(this.tarefaParaAssumir.replace("@", tarefaId))).andExpect(status().isOk());
    	
		//Realiza a préautuação da petição física.
		super.mockMvc.perform(post("/api/actions/preautuar/execute").contentType(MediaType.APPLICATION_JSON)
	    		.content(this.peticaoFisicaParaPreautuacao.replace("@", peticaoId))).andExpect(status().isOk());
		
		//Recupera a(s) tarefa(s) do autuador.
		tarefaObject = super.mockMvc.perform(get("/api/workflow/tarefas").header("login", "autuador")).andExpect(status().isOk())
			.andExpect(jsonPath("$[0].nome", is("autuar"))).andReturn().getResponse().getContentAsString();
		tarefaId = getTarefaId(tarefaObject);
		
    	//Assumir a(s) tarefa(s) do autuador.
		super.mockMvc.perform(get("/api/actions/assumir-tarefa/execute").contentType(MediaType.APPLICATION_JSON)
	    		.content(this.tarefaParaAssumir.replace("@", tarefaId))).andExpect(status().isOk());
		
		//Realiza a autuação.
		super.mockMvc.perform(post("/api/actions/autuar/execute").contentType(MediaType.APPLICATION_JSON)
			.content(this.peticaoValidaParaAutuacao.replace("@", peticaoId))).andExpect(status().isOk());
		
		//Recupera a(s) tarefa(s) do distribuidor.
		tarefaObject = super.mockMvc.perform(get("/api/workflow/tarefas").header("login", "distribuidor")).andExpect(status().isOk())
			.andExpect(jsonPath("$[0].nome", is("distribuir-processo"))).andReturn().getResponse().getContentAsString();
		tarefaId = getTarefaId(tarefaObject);
		
    	//Assumir a(s) tarefa(s) do distribuidor.
		super.mockMvc.perform(get("/api/actions/assumir-tarefa/execute").contentType(MediaType.APPLICATION_JSON)
	    		.content(this.tarefaParaAssumir.replace("@", tarefaId))).andExpect(status().isOk());
		
		//Realiza a distribuição.
		super.mockMvc.perform(post("/api/actions/distribuir-processo/execute").contentType(MediaType.APPLICATION_JSON)
			.content(this.peticaoAutuadaParaDistribuicao.replace("@", peticaoId))).andExpect(status().isOk()).andExpect(jsonPath("$.relator", is(28)));

    }
    
    @Test
    public void executarAcaoRejeitarPeticao() throws Exception {
    	
    	String peticaoId = "";
    	
    	//Envia a petição eletrônica.
    	peticaoId = super.mockMvc.perform(post("/api/actions/registrar-peticao-eletronica/execute").header("login", "peticionador").contentType(MediaType.APPLICATION_JSON)
    		.content(this.peticaoEletronica)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		//Recupera a(s) tarefa(s) do autuador.
		super.mockMvc.perform(get("/api/workflow/tarefas").header("login", "autuador")).andExpect(status().isOk())
			.andExpect(jsonPath("$[0].nome", is("autuar")));
		
		//Realiza a autuação.
		super.mockMvc.perform(post("/api/actions/autuar/execute").contentType(MediaType.APPLICATION_JSON)
			.content(this.peticaoInvalidaParaAutuacao.replace("@", peticaoId))).andExpect(status().isOk());
		
    }
    
    private String getTarefaId(String json) {
    	return (String) new JSONArray(json).getJSONObject(0).get("id");
    }
}
