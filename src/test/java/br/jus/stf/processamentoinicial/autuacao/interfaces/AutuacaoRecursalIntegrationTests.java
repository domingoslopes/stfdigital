package br.jus.stf.processamentoinicial.autuacao.interfaces;

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
import org.activiti.engine.impl.util.json.JSONObject;
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
import br.jus.stf.processamentoinicial.autuacao.infra.eventbus.PeticaoStatusIndexadorConsumer;
import br.jus.stf.processamentoinicial.recursaledistribuicao.infra.eventbus.ProcessoDistribuidoIndexadorConsumer;

/**
 * Executa os testes de integração do processo de autuação de recursais.
 * 
 * @author Anderson.Araujo
 * 
 * @since 19.01.2016
 *
 */
public class AutuacaoRecursalIntegrationTests extends AbstractIntegrationTests {
	
	private String peticaoAutuadaParaDistribuicao;
	private String peticaoFisicaParaRegistro;
	private String peticaoFisicaParaPreautuacao;
	private String processoInaptoParaAnalisePressupostos;
	private String processoInaptoRevisadoParaApto;
	private String processoComRepercussaoGeral;
	private String processoValidoParaAutuacao;
	private String tarefaParaAssumir;
	
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

		//Cria um objeto contendo os dados da petição física a ser usado no teste do registro da petição física.
		StringBuilder peticaoFisica =  new StringBuilder();
		peticaoFisica.append("{\"resources\": [{\"formaRecebimento\":\"SEDEX\",");
		peticaoFisica.append("\"quantidadeVolumes\":2,");
		peticaoFisica.append("\"quantidadeApensos\":1,");
		peticaoFisica.append("\"tipoProcesso\":\"RECURSAL\",");
		peticaoFisica.append("\"numeroSedex\":\"SR123456789BR\"}]}");
		this.peticaoFisicaParaRegistro = peticaoFisica.toString();
		
		//Cria um objeto contendo os dados de uma petição física a ser usado no processo de préautuação.
		StringBuilder peticaoFisicaParaPreautuacao =  new StringBuilder();
		peticaoFisicaParaPreautuacao.append("{\"resources\": ");
		peticaoFisicaParaPreautuacao.append("[{\"peticaoId\": @,");
		peticaoFisicaParaPreautuacao.append("\"valida\":true,");
		peticaoFisicaParaPreautuacao.append("\"motivo\":\"Motivo\",");
		peticaoFisicaParaPreautuacao.append("\"preferencias\":[],");
		peticaoFisicaParaPreautuacao.append("\"classeId\":\"ADI\"}]}");
		this.peticaoFisicaParaPreautuacao = peticaoFisicaParaPreautuacao.toString();
		
		//Cria um objeto para analise de pressupostos
		StringBuilder processoInaptoParaAnalisePressupostos =  new StringBuilder();
		processoInaptoParaAnalisePressupostos.append("{\"resources\": ");
		processoInaptoParaAnalisePressupostos.append("[{\"processoId\": @,");
		processoInaptoParaAnalisePressupostos.append("\"classificacao\": \"INAPTO\",");
		processoInaptoParaAnalisePressupostos.append("\"motivos\": { \"2\" : \"TESTE\", \"3\" : \"TESTE\" },");
		processoInaptoParaAnalisePressupostos.append("\"observacao\":\"TESTE\"}]}");
		this.processoInaptoParaAnalisePressupostos = processoInaptoParaAnalisePressupostos.toString();
		
		//Cria um objeto para revisao de analise de pressupostos
		StringBuilder processoInaptoRevisadoParaApto =  new StringBuilder();
		processoInaptoRevisadoParaApto.append("{\"resources\": ");
		processoInaptoRevisadoParaApto.append("[{\"processoId\": @,");
		processoInaptoRevisadoParaApto.append("\"classificacao\": \"APTO\",");
		processoInaptoRevisadoParaApto.append("\"motivos\": { \"2\" : \"TESTE\", \"3\" : \"TESTE\" },");
		processoInaptoRevisadoParaApto.append("\"observacao\":\"TESTE\"}]}");
		this.processoInaptoRevisadoParaApto = processoInaptoRevisadoParaApto.toString();
		
		//Cria um objeto para repercussao geral
		StringBuilder processoComRepercussaoGeral =  new StringBuilder();
		processoComRepercussaoGeral.append("{\"resources\": ");
		processoComRepercussaoGeral.append("[{\"processoId\": @,");
		processoComRepercussaoGeral.append("\"assuntos\": [\"864\"],");
		processoComRepercussaoGeral.append("\"teses\": [56],");
		processoComRepercussaoGeral.append("\"observacao\":\"TESTE\"}]}");
		this.processoComRepercussaoGeral = processoComRepercussaoGeral.toString();
		
	    //Cria um objeto contendo os dados da petição eletrônica a ser usado no teste.
	    StringBuilder processoValidoParaAutuacao =  new StringBuilder();
	    processoValidoParaAutuacao.append("{\"resources\": ");
	    processoValidoParaAutuacao.append("[{\"processoId\": @,");
	    processoValidoParaAutuacao.append("\"partesPoloAtivo\":[\"João\"],");
	    processoValidoParaAutuacao.append("\"partesPoloPassivo\":[\"Maria\"],");
	    processoValidoParaAutuacao.append("\"assuntos\": [\"864\"]}]}");
		this.processoValidoParaAutuacao = processoValidoParaAutuacao.toString();
		
		this.tarefaParaAssumir = "{\"resources\": [{\"tarefaId\": @}]}";
	}
	
    @Test
    public void executarAcaoRegistroPeticaoRecursal() throws Exception {
    	
    	String peticaoId = "";
    	String processoId = "";
    	String tarefaObject = "";
    	
    	//Envia a petição eletrônica.
    	peticaoId = super.mockMvc.perform(post("/api/actions/registrar-peticao-fisica/execute").header("login", "recebedor").contentType(MediaType.APPLICATION_JSON)
    		.content(this.peticaoFisicaParaRegistro)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
    	//Recupera a(s) tarefa(s) do préautuador.
    	tarefaObject = super.mockMvc.perform(get("/api/workflow/tarefas/papeis").header("login", "preautuador-recursal")).andExpect(status().isOk())
			.andExpect(jsonPath("$[0].nome", is("preautuar-recursal"))).andReturn().getResponse().getContentAsString();
    	
    	//Assumir a(s) tarefa(s) do préautuador.
    	assumirTarefa(tarefaObject);
    	    	
		//Realiza a préautuação da petição física.
		super.mockMvc.perform(post("/api/actions/preautuar-recursal/execute").contentType(MediaType.APPLICATION_JSON)
	    		.content(this.peticaoFisicaParaPreautuacao.replace("@", peticaoId))).andExpect(status().isOk());
		
		//Consulta Id processo.
		processoId = getProcessoId(peticaoId);
		
		//Recupera a(s) tarefa(s) do analista.
		tarefaObject = super.mockMvc.perform(get("/api/workflow/tarefas/papeis").header("login", "analista-pressupostos")).andExpect(status().isOk())
			.andExpect(jsonPath("$[0].nome", is("analisar-pressupostos-formais"))).andReturn().getResponse().getContentAsString();
		
    	//Assumir a(s) tarefa(s) do analista.
		assumirTarefa(tarefaObject);
		
		//Realiza a análise.
		super.mockMvc.perform(post("/api/actions/analisar-pressupostos-formais/execute").contentType(MediaType.APPLICATION_JSON)
			.content(this.processoInaptoParaAnalisePressupostos.replace("@", processoId))).andExpect(status().isOk());
		
		//Recupera a(s) tarefa(s) do revisor.
		tarefaObject = super.mockMvc.perform(get("/api/workflow/tarefas/papeis").header("login", "revisor-processo-ri")).andExpect(status().isOk())
			.andExpect(jsonPath("$[0].nome", is("revisar-processo-inapto"))).andReturn().getResponse().getContentAsString();
		
    	//Assumir a(s) tarefa(s) do revisor.
		assumirTarefa(tarefaObject);
		
		//Realiza a revisão.
		super.mockMvc.perform(post("/api/actions/revisar-processo-inapto/execute").contentType(MediaType.APPLICATION_JSON)
			.content(this.processoInaptoRevisadoParaApto.replace("@", processoId))).andExpect(status().isOk());
		
		//Recupera a(s) tarefa(s) do analista.
		tarefaObject = super.mockMvc.perform(get("/api/workflow/tarefas/papeis").header("login", "analista-repercussao-g")).andExpect(status().isOk())
			.andExpect(jsonPath("$[0].nome", is("analisar-repercussao-geral"))).andReturn().getResponse().getContentAsString();
		
    	//Assumir a(s) tarefa(s) do analista.
		assumirTarefa(tarefaObject);
		
		//Realiza a análise.
		super.mockMvc.perform(post("/api/actions/analisar-repercussao-geral/execute").contentType(MediaType.APPLICATION_JSON)
			.content(this.processoComRepercussaoGeral.replace("@", processoId))).andExpect(status().isOk());
		
		//Recupera a(s) tarefa(s) do revisor.
		tarefaObject = super.mockMvc.perform(get("/api/workflow/tarefas/papeis").header("login", "revisor-repercussao-g")).andExpect(status().isOk())
			.andExpect(jsonPath("$[0].nome", is("revisar-repercussao-geral"))).andReturn().getResponse().getContentAsString();
		
    	//Assumir a(s) tarefa(s) do revisor.
		assumirTarefa(tarefaObject);
		
		//Realiza a revisão.
		super.mockMvc.perform(post("/api/actions/revisar-repercussao-geral/execute").contentType(MediaType.APPLICATION_JSON)
			.content(this.processoComRepercussaoGeral.replace("@", processoId))).andExpect(status().isOk());
		
		//Recupera a(s) tarefa(s) do autuador.
    	tarefaObject = super.mockMvc.perform(get("/api/workflow/tarefas/papeis").header("login", "autuador-recursal")).andExpect(status().isOk())
			.andExpect(jsonPath("$[0].nome", is("autuar-recursal"))).andReturn().getResponse().getContentAsString();
		
		assumirTarefa(tarefaObject);
		
		//Realiza a autuação.
		super.mockMvc.perform(post("/api/actions/autuar-recursal/execute").contentType(MediaType.APPLICATION_JSON)
			.content(this.processoValidoParaAutuacao.replace("@", processoId))).andExpect(status().isOk());
		
		//Recupera a(s) tarefa(s) do distribuidor.
		tarefaObject = super.mockMvc.perform(get("/api/workflow/tarefas/papeis").header("login", "distribuidor")).andExpect(status().isOk())
			.andExpect(jsonPath("$[0].nome", is("distribuir-processo"))).andReturn().getResponse().getContentAsString();
		
    	//Assumir a(s) tarefa(s) do distribuidor.
		assumirTarefa(tarefaObject);
		
		//Realiza a distribuição.
		super.mockMvc.perform(post("/api/actions/distribuir-processo/execute").contentType(MediaType.APPLICATION_JSON)
			.content(this.peticaoAutuadaParaDistribuicao.replace("@", peticaoId))).andExpect(status().isOk()).andExpect(jsonPath("$.relator", is(28)));
    }
    
    private void assumirTarefa(String tarefaObject) throws Exception {
    	String tarefaId = getId(tarefaObject);
		super.mockMvc.perform(post("/api/actions/assumir-tarefa/execute").contentType(MediaType.APPLICATION_JSON)
	    		.content(this.tarefaParaAssumir.replace("@", tarefaId))).andExpect(status().isOk());
    }
    
    private String getId(String json) {
    	return ((Integer) new JSONArray(json).getJSONObject(0).get("id")).toString();
    }
    
    private String getProcessoId(String peticaoId) throws Exception {
		String json = super.mockMvc.perform(get("/api/peticoes/" + peticaoId + "/processo").contentType(MediaType.APPLICATION_JSON))
				.andReturn().getResponse().getContentAsString();
		return ((Integer) new JSONObject(json).get("id")).toString();
    }
    
}
