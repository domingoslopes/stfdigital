package br.jus.stf.processamentoinicial.autuacao.interfaces;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import br.jus.stf.plataforma.shared.tests.AbstractIntegrationTests;

/**
 * @author Rodrigo Barreiros
 * @author Anderson.Araujo
 * 
 * @since 1.0.0
 * @since 17.06.2015
 */
public class AutuacaoOriginariosIntegrationTests extends AbstractIntegrationTests {

	private String peticaoValidaParaAutuacao;
	private String peticaoAutuadaParaDistribuicao;
	private String peticaoEletronica;
	private String peticaoFisicaParaRegistro;
	private String peticaoFisicaParaPreautuacao;
	private String peticaoInvalidaParaAutuacao;
	
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
		peticaoAutuadaParaDistribuicao.append("{\"ministroId\":36}");
		this.peticaoAutuadaParaDistribuicao = peticaoAutuadaParaDistribuicao.toString();
		
		//Envia um documento para que seja obtido o seu ID. Este será usado para simular o teste de envio de uma petição eletrônica.
		
		String idDoc = "";
		String nomeArquivo = "teste_arq_temp.pdf";
		String mime = "application/pdf";
		String caminho = "pdf/archimate.pdf";
		
		byte[] arquivo = IOUtils.toByteArray(new ClassPathResource(caminho).getInputStream());

	    MockMultipartFile mockArquivo = new MockMultipartFile("file", nomeArquivo, mime, arquivo);
		
	    //Envia um documento antes de enviar a petição.
	    idDoc = mockMvc.perform(fileUpload("/api/documentos/upload").file(mockArquivo).contentType(MediaType.MULTIPART_FORM_DATA).content(arquivo))
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
		peticaoFisica.append("\"numeroSedex\":\"SR123456789BR\"}");
		this.peticaoFisicaParaRegistro = peticaoFisica.toString();
		
		//Cria um objeto contendo os dados de uma petição física a ser usado no processo de préautuação.
		StringBuilder peticaoFisicaParaPreautuacao =  new StringBuilder();
		peticaoFisicaParaPreautuacao.append("{\"classeId\":\"ADI\",");
		peticaoFisicaParaPreautuacao.append("\"valida\":true,");
		peticaoFisicaParaPreautuacao.append("\"motivo\":\"\"}");
		this.peticaoFisicaParaPreautuacao = peticaoFisicaParaPreautuacao.toString();
		
		//Cria um objeto para ser usado no processo de rejeição de uma petição.
		StringBuilder peticaoInValidaParaAutuacao =  new StringBuilder();
		peticaoInValidaParaAutuacao.append("{\"classeId\":\"ADI\",");
		peticaoInValidaParaAutuacao.append("\"valida\":false,");
		peticaoInValidaParaAutuacao.append("\"motivo\":\"Petição inválida\"}");
		this.peticaoInvalidaParaAutuacao = peticaoInValidaParaAutuacao.toString();
	}
	
	@Test
	public void distribuirPeticaoEletronica() throws Exception {
		String peticaoId = "";
				
		//Envia a petição eletrônica
		peticaoId = this.mockMvc.perform(post("/api/peticoes/").contentType(MediaType.APPLICATION_JSON)
			.content(this.peticaoEletronica)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		//Recupera a(s) tarefa(s) do autuador.
		this.mockMvc.perform(get("/api/workflow/tarefas").header("papel", "autuador")).andExpect(status().isOk())
			.andExpect(jsonPath("$[0].descricao", is("Autuar Processo")));
		
		//Realiza a autuação.
		this.mockMvc.perform(post("/api/peticoes/" + peticaoId + "/autuar").contentType(MediaType.APPLICATION_JSON)
			.content(this.peticaoValidaParaAutuacao)).andExpect(status().isOk());
		
		//Recupera a(s) tarefa(s) do distribuidor.
		this.mockMvc.perform(get("/api/workflow/tarefas").header("papel", "distribuidor")).andExpect(status().isOk())
			.andExpect(jsonPath("$[0].descricao", is("Distribuir Processo")));
		
		//Realiza a distribuição.
		this.mockMvc.perform(post("/api/peticoes/" + peticaoId + "/distribuir").contentType(MediaType.APPLICATION_JSON)
			.content(this.peticaoAutuadaParaDistribuicao)).andExpect(status().isOk()).andExpect(jsonPath("$.relator", is(36)));
		
		//Recupera as partes da petição.
		this.mockMvc.perform(get("/api/peticoes/" + peticaoId + "/partes").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk()).andExpect(jsonPath("$[0].PoloAtivo").value("[5, 6]"));
		
		//TODO: verificar, pois falha de forma intermitente ao executar todos os testes
		//Tenta recuperar as tarefas do autuador. A ideia é receber uma lista vazia, já que a instância do processo foi encerrada.
//		this.mockMvc.perform(get("/api/workflow/tarefas").header("papel", "autuador")).andExpect(status().isOk())
//			.andExpect(jsonPath("$", Matchers.empty()));
	}
	
	@Test
	public void distribuirPeticaoFisica() throws Exception {
		
		String peticaoId = "";
		
		//Registra a petição física.
		peticaoId = this.mockMvc.perform(post("/api/peticoes/fisicas").contentType(MediaType.APPLICATION_JSON)
			.content(peticaoFisicaParaRegistro.toString())).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		//Recupera a(s) tarefa(s) do préautuador.
		this.mockMvc.perform(get("/api/workflow/tarefas").header("papel", "preautuador")).andExpect(status().isOk())
			.andExpect(jsonPath("$[0].descricao", is("Pré-Autuar Processo")));
		
		//Faz a préautuação da petição registrada.
		this.mockMvc.perform(post("/api/peticoes/fisicas/" + peticaoId + "/preautuar").contentType(MediaType.APPLICATION_JSON)
				.content(peticaoFisicaParaPreautuacao.toString())).andExpect(status().isOk());
		
		//Recupera a(s) tarefa(s) do autuador.
		this.mockMvc.perform(get("/api/workflow/tarefas").header("papel", "autuador")).andExpect(status().isOk())
			.andExpect(jsonPath("$[0].descricao", is("Autuar Processo")));
		
		//Realiza a autuação da petição préautuada.
		this.mockMvc.perform(post("/api/peticoes/" + peticaoId + "/autuar").contentType(MediaType.APPLICATION_JSON)
				.content(this.peticaoValidaParaAutuacao)).andExpect(status().isOk());
		
		//Recupera a(s) tarefa(s) do distribuidor.
		this.mockMvc.perform(get("/api/workflow/tarefas").header("papel", "distribuidor")).andExpect(status().isOk())
			.andExpect(jsonPath("$[0].descricao", is("Distribuir Processo")));
		
		//Realiza a distribuição.
		this.mockMvc.perform(post("/api/peticoes/" + peticaoId + "/distribuir").contentType(MediaType.APPLICATION_JSON)
			.content(this.peticaoAutuadaParaDistribuicao)).andExpect(status().isOk()).andExpect(jsonPath("$.relator", is(36)));
		
		//TODO: verificar, pois falha de forma intermitente ao executar todos os testes
		//Tenta recuperar as tarefas do autuador. A ideia é receber uma lista vazia, já que a instância do processo foi encerrada.
//		this.mockMvc.perform(get("/api/workflow/tarefas").header("papel", "autuador")).andExpect(status().isOk())
//			.andExpect(jsonPath("$", Matchers.empty()));
	}
	
	@Test
	public void rejeitarPeticao() throws Exception{
		
		String peticaoId = "";
		
		//Envia a petição eletrônica
		peticaoId = this.mockMvc.perform(post("/api/peticoes/").contentType(MediaType.APPLICATION_JSON)
			.content(this.peticaoEletronica)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		//Recupera a(s) tarefa(s) do autuador.
		this.mockMvc.perform(get("/api/workflow/tarefas").header("papel", "autuador")).andExpect(status().isOk())
			.andExpect(jsonPath("$[0].descricao", is("Autuar Processo")));
		
		//Realiza a autuação.
		this.mockMvc.perform(post("/api/peticoes/" + peticaoId + "/autuar").contentType(MediaType.APPLICATION_JSON)
			.content(this.peticaoInvalidaParaAutuacao)).andExpect(status().isOk());
		
		//TODO: verificar, pois falha de forma intermitente ao executar todos os testes
		//Tenta recuperar as tarefas do autuador. A ideia é receber uma lista vazia, já que a instância do processo foi encerrada.
//		this.mockMvc.perform(get("/api/workflow/tarefas").header("papel", "devolvedor")).andExpect(status().isOk())
//			.andExpect(jsonPath("$", Matchers.empty()));
	}
}
