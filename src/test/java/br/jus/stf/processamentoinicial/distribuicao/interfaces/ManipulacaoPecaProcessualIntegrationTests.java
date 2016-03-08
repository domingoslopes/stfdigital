package br.jus.stf.processamentoinicial.distribuicao.interfaces;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import br.jus.stf.plataforma.shared.tests.AbstractIntegrationTests;

import com.jayway.jsonpath.JsonPath;

/**
 * Realiza os testes de intergação de manipulação de peças processuais.
 * 
 * @author Anderson.Araujo
 * @since 17.02.2016
 *
 */
public class ManipulacaoPecaProcessualIntegrationTests extends AbstractIntegrationTests {
	private String salvarPecasCommand;
	private String editarPecaCommand;
	private String excluirPecasCommand;
	private String dividirPecasCommand;
	private String unirPecasCommand;
	private String juntarPecaCommand;
	private String peticaoEletronica;
	private String peticaoValidaParaAutuacao;
	private String peticaoAutuadaParaDistribuicao;
	private String tarefaParaAssumir;
	
	@Before
	public void setUp() throws UnsupportedEncodingException, Exception{
				
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
		
		//Cria um objeto para ser usado no processo de autuação de uma petição válida.
		StringBuilder peticaoEletronicaValidaParaAutuacao =  new StringBuilder();
		peticaoEletronicaValidaParaAutuacao.append("{\"resources\": ");
		peticaoEletronicaValidaParaAutuacao.append("[{\"peticaoId\": @,");
		peticaoEletronicaValidaParaAutuacao.append("\"classeId\":\"ADI\",");
		peticaoEletronicaValidaParaAutuacao.append("\"valida\":true,");
		peticaoEletronicaValidaParaAutuacao.append("\"partesPoloAtivo\":[1, 2],");
		peticaoEletronicaValidaParaAutuacao.append("\"partesPoloPassivo\":[3, 4],");
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
		
		this.tarefaParaAssumir = "{\"resources\": [{\"tarefaId\": @}]}";
	}
	
	@Test
    public void executarAcaoManterPecaProcesso() throws Exception {
    	
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
		
		//Envia um documento para que seja obtido o seu ID. Este será usado para inserir uma peça.
		String documentoTemporarioId = "";
		String nomeArquivo = "teste_arq_temp.pdf";
		String mime = "application/pdf";
		String caminho = "certification/pdf-de-teste-assinado-02.pdf";
		
		byte[] arquivo = IOUtils.toByteArray(new ClassPathResource(caminho).getInputStream());

	    MockMultipartFile mockArquivo = new MockMultipartFile("file", nomeArquivo, mime, arquivo);
		
	    //Envia o arquivo temporário.
	    documentoTemporarioId = mockMvc.perform(fileUpload("/api/documentos/upload/assinado").file(mockArquivo).contentType(MediaType.MULTIPART_FORM_DATA).content(arquivo))
	    	.andExpect(status().is2xxSuccessful()).andReturn().getResponse().getContentAsString();
	    
	    String processoDto = getProcesso(peticaoId);
	    String processoId = getProcessoId(processoDto);
	    
	    salvarPecasCommand = "{\"resources\":[{\"processoId\": " + processoId + ", \"pecas\": [{\"documentoTemporarioId\":\"" + documentoTemporarioId + "\", \"tipoPecaId\":1, \"visibilidade\":\"PUBLICO\", \"descricao\":\"xxx\"}]}]}";
	    
	    //Insere a peça.
  		super.mockMvc.perform(post("/api/actions/inserir-pecas/execute").header("login", "organizador-pecas").contentType(MediaType.APPLICATION_JSON)
  			.content(salvarPecasCommand)).andExpect(status().isOk());
		
  		//Recupera novamente o processo após a inserção da segunda peça.
  		processoDto = getProcesso(peticaoId);
  		
  		juntarPecaCommand = prepararPecaParaJuntada(processoId);
  		
  		//Realiza a juntada da peça.
  		super.mockMvc.perform(post("/api/actions/juntar-peca/execute").header("login", "organizador-pecas").contentType(MediaType.APPLICATION_JSON)
  			.content(juntarPecaCommand)).andExpect(status().isOk());
  		
  		//Recupera novamente o processo após a juntada da segunda peça.
  		processoDto = getProcesso(peticaoId);
  		
  		//Recupera a peça que acabou de ser inserida para ser dividida em duas.
  		editarPecaCommand = prepararPecaParaEdicao(processoId);
  		
  		super.mockMvc.perform(post("/api/actions/editar-peca/execute").header("login", "organizador-pecas").contentType(MediaType.APPLICATION_JSON)
  	  			.content(editarPecaCommand)).andExpect(status().isOk());
  		
  		//Recupera novamente o processo após a inserção da segunda peça.
  		processoDto = getProcesso(peticaoId);
  		
  		//Recupera a peça que acabou de ser inserida para ser dividida em duas.
  		dividirPecasCommand = prepararPecaParaDivisao(processoId);
  		
  		//Divide a peça.
  		super.mockMvc.perform(post("/api/actions/dividir-peca/execute").header("login", "organizador-pecas").contentType(MediaType.APPLICATION_JSON)
  	  			.content(dividirPecasCommand)).andExpect(status().isOk());
  		
  		//Recupera novamente o processo após a divisão da segunda peça.
  		processoDto = getProcesso(peticaoId);
  		
  		unirPecasCommand = prepararPecaParaUniao(processoId);
  		
  		//Une as duas peças.
  		super.mockMvc.perform(post("/api/actions/unir-pecas/execute").header("login", "organizador-pecas").contentType(MediaType.APPLICATION_JSON)
  			.content(unirPecasCommand)).andExpect(status().isOk());
  		
  		//Recupera novamente o processo após a união de peças.
  		processoDto = getProcesso(peticaoId);
  		
  		excluirPecasCommand = getPecaParaExclusao(processoId, processoDto);
  		
  		//Exclui as peças.
  		super.mockMvc.perform(post("/api/actions/excluir-pecas/execute").header("login", "organizador-pecas").contentType(MediaType.APPLICATION_JSON)
  			.content(excluirPecasCommand)).andExpect(status().isOk());
    }
	
	private String prepararPecaParaJuntada(String processoId) throws UnsupportedEncodingException, Exception{
		String peca = getPeca(processoId);
		String pecaId = (JsonPath.read(peca, "$.pecaId")).toString();
		
		StringBuilder json = new StringBuilder();
		json.append("{\"resources\": ");
		json.append("[{\"processoId\": " + processoId + ", \"pecaId\":" + pecaId + "}]}");
		
		return json.toString();
	}
	
	private String prepararPecaParaEdicao(String processoId) throws UnsupportedEncodingException, Exception{
		String peca = getPeca(processoId);
		String pecaId = (JsonPath.read(peca, "$.pecaId")).toString();
		String tipoPecaId = "2";
		String descricao = "peca editada";
		String numeroOrdem = "2";
		String visibilidade = "PENDENTE_VISUALIZACAO";
		
		StringBuilder json = new StringBuilder();
		json.append("{\"resources\": ");
		json.append("[{\"pecaId\":" + pecaId + ", \"tipoPecaId\":" + tipoPecaId + ", \"descricao\": \"" + descricao + "\", \"numeroOrdem\": " + numeroOrdem + ", \"visibilidade\": \"" + visibilidade + "\"}]}");
		
		return json.toString();
	}
	
	private String prepararPecaParaUniao(String processoId) throws UnsupportedEncodingException, Exception{
		List<String> pecas = getPecasParaUniao(processoId);
		StringBuilder json = new StringBuilder();
		json.append("{\"resources\": ");
		json.append("[{\"processoId\":" + processoId + ", \"pecas\":[" + pecas.get(0) + "," + pecas.get(1) + "]}]}");
		
		return json.toString();
	}
	
	private String prepararPecaParaDivisao(String processoId) throws UnsupportedEncodingException, Exception{
		String peca = getPeca(processoId);
		String pecaId = (JsonPath.read(peca, "$.pecaId")).toString();
		String visibilidade = (JsonPath.read(peca, "$.visibilidade")).toString();
		String situacao = (JsonPath.read(peca, "$.situacao")).toString();
						
		StringBuilder json = new StringBuilder();
		json.append("{\"resources\": ");
		json.append("[{\"processoId\":" + processoId + ", \"pecaId\": " + pecaId + ", \"pecas\":");
		json.append("[{\"documentoTemporarioId\":\"\", \"tipoPecaId\":1, \"visibilidade\":\"" + visibilidade + "\", \"situacao\":\"" + situacao + "\", \"descricao\":\"primeira parte peca\", \"paginaInicial\":1, \"paginaFinal\":2}, ");
		json.append("{\"tipoPecaId\":1, \"visibilidade\":\"" + visibilidade + "\", \"situacao\":\"" + situacao + "\", \"descricao\":\"segunda parte peca\", \"paginaInicial\":3, \"paginaFinal\":5}]}]}");
		
		return json.toString();
	}
	
	private String getPeca(String processoId) throws UnsupportedEncodingException, Exception{
		String json = super.mockMvc.perform(get("/api/processos/" + processoId + "/pecas").contentType(MediaType.APPLICATION_JSON))
				.andReturn().getResponse().getContentAsString();
		
		return (JsonPath.read(json, "$[1]")).toString();
	}
	
	private List<String> getPecasParaUniao(String processoId) throws UnsupportedEncodingException, Exception{
		String json = super.mockMvc.perform(get("/api/processos/" + processoId + "/pecas").contentType(MediaType.APPLICATION_JSON))
				.andReturn().getResponse().getContentAsString();
		List<String> pecas = new LinkedList<String>();
		pecas.add((JsonPath.read(json, "$[1].pecaId")).toString());
		pecas.add((JsonPath.read(json, "$[2].pecaId")).toString());
		
		return pecas;
	}
	
	private void assumirTarefa(String json) throws Exception {
    	String tarefaId = ((Integer) JsonPath.read(json, "$[0].id")).toString();
		super.mockMvc.perform(post("/api/actions/assumir-tarefa/execute").contentType(MediaType.APPLICATION_JSON)
	    		.content(this.tarefaParaAssumir.replace("@", tarefaId))).andExpect(status().isOk());
    }
	
	private String getProcesso(String peticaoId) throws Exception {
		String json = super.mockMvc.perform(get("/api/peticoes/" + peticaoId + "/processo").contentType(MediaType.APPLICATION_JSON))
				.andReturn().getResponse().getContentAsString();
		
		return (JsonPath.read(json, "$")).toString();
    }
	
	private String getPecaParaExclusao(String processoId, String processo){
		String peca1 = (JsonPath.read(processo, "$.pecas[1].sequencial")).toString();
		
		StringBuilder pecas =  new StringBuilder();
		pecas.append("{\"resources\": [{\"processoId\":" + processoId + ",");
		pecas.append("\"pecas\":[" + peca1 + "]}]}");
		
		return pecas.toString();
	}
	
	private String getProcessoId(String processo){
		return (JsonPath.read(processo, "$.id")).toString();
	}
}
