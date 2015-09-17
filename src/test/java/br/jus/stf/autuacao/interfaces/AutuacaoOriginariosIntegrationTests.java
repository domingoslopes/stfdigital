package br.jus.stf.autuacao.interfaces;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
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

import br.jus.stf.AbstractIntegrationTests;

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
		peticaoEletronicaValidaParaAutuacao.append("{\"classe\":\"ADI\",");
		peticaoEletronicaValidaParaAutuacao.append("\"valida\":true,");
		peticaoEletronicaValidaParaAutuacao.append("\"motivo\":\"\"}");
		this.peticaoValidaParaAutuacao = peticaoEletronicaValidaParaAutuacao.toString();
		
		//Cria um objeto para ser usado no processo de distribuição de uma petição.
		StringBuilder peticaoAutuadaParaDistribuicao =  new StringBuilder();
		peticaoAutuadaParaDistribuicao.append("{\"idRelator\":36}");
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
		peticaoEletronica.append("{\"classe\":\"HC\",");
		peticaoEletronica.append("\"partesPoloAtivo\":[1, 2],");
		peticaoEletronica.append("\"partesPoloPassivo\":[3, 4],");
		peticaoEletronica.append("\"documentos\":[\"" + idDoc + "\"]}");
		this.peticaoEletronica = peticaoEletronica.toString();
		
		//Cria um objeto contendo os dados da petição física a ser usado no teste do registro da petição física.
		StringBuilder peticaoFisica =  new StringBuilder();
		peticaoFisica.append("{\"formaRecebimento\":\"2\",");
		peticaoFisica.append("\"quantidadeVolumes\":2,");
		peticaoFisica.append("\"quantidadeApensos\":1,");
		peticaoFisica.append("\"numeroSedex\":\"SR123456789BR\"}");
		this.peticaoFisicaParaRegistro = peticaoFisica.toString();
		
		//Cria um objeto contendo os dados de uma petição física a ser usado no processo de préautuação.
		StringBuilder peticaoFisicaParaPreautuacao =  new StringBuilder();
		peticaoFisicaParaPreautuacao.append("{\"classeSugerida\":\"ADI\"}");
		this.peticaoFisicaParaPreautuacao = peticaoFisicaParaPreautuacao.toString();
		
		//Cria um objeto para ser usado no processo de rejeição de uma petição.
		StringBuilder peticaoInValidaParaAutuacao =  new StringBuilder();
		peticaoInValidaParaAutuacao.append("{\"classe\":\"ADI\",");
		peticaoInValidaParaAutuacao.append("\"valida\":false,");
		peticaoInValidaParaAutuacao.append("\"motivo\":\"Petição inválida\"}");
		this.peticaoInvalidaParaAutuacao = peticaoInValidaParaAutuacao.toString();
	}
	
	@Test
	public void distribuirPeticaoEletronica() throws Exception {
		String idPeticao = "";
		
		//Envia a petição eletrônica
		idPeticao = this.mockMvc.perform(post("/api/peticao/").contentType(MediaType.APPLICATION_JSON)
			.content(this.peticaoEletronica)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		//Realiza a autuação.
		this.mockMvc.perform(post("/api/peticao/" + idPeticao + "/autuacao").contentType(MediaType.APPLICATION_JSON)
			.content(this.peticaoValidaParaAutuacao)).andExpect(status().isOk());
		
		//Realiza a distribuição.
		this.mockMvc.perform(post("/api/peticao/" + idPeticao + "/distribuicao").contentType(MediaType.APPLICATION_JSON)
			.content(this.peticaoAutuadaParaDistribuicao)).andExpect(status().isOk()).andExpect(jsonPath("$.relator", is(36)));
	}
	
	/*
	@Test
	public void distribuirPeticaoFisica() throws Exception {
		
		String idPeticao = "";
		
		//Registra a petição física.
		idPeticao = this.mockMvc.perform(post("/api/peticao/fisica").contentType(MediaType.APPLICATION_JSON)
			.content(peticaoFisicaParaRegistro.toString())).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		//Faz a préautuação da petição registrada.
		this.mockMvc.perform(post("/api/peticao/" + idPeticao + "/preautuacao").contentType(MediaType.APPLICATION_JSON)
				.content(peticaoFisicaParaPreautuacao.toString())).andExpect(status().isOk());
		
		//Realiza a autuação da petição préautuada.
		this.mockMvc.perform(post("/api/peticao/" + idPeticao + "/autuacao").contentType(MediaType.APPLICATION_JSON)
				.content(this.peticaoValidaParaAutuacao)).andExpect(status().isOk());
		
		//Realiza a distribuição da petição.
		this.mockMvc.perform(post("/api/peticao/" + idPeticao + "/distribuicao").contentType(MediaType.APPLICATION_JSON)
				.content(this.peticaoAutuadaParaDistribuicao)).andExpect(status().isOk()).andExpect(jsonPath("$.relator", is(36)));
	}
	*/
	/*
	@Test
	public void devolverPeticao() throws Exception{
		String idPeticao = "";
		
		//Registra a petição física.
		idPeticao = this.mockMvc.perform(post("/api/peticao/fisica").contentType(MediaType.APPLICATION_JSON)
			.content(peticaoFisicaParaRegistro.toString())).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		//Faz a préautuação da petição registrada.
		this.mockMvc.perform(post("/api/peticao/" + idPeticao + "/preautuacao").contentType(MediaType.APPLICATION_JSON)
				.content(peticaoFisicaParaPreautuacao.toString())).andExpect(status().isOk());
		
		//Realiza a autuação da petição préautuada.
		this.mockMvc.perform(post("/api/peticao/" + idPeticao + "/autuacao").contentType(MediaType.APPLICATION_JSON)
				.content(this.peticaoInvalidaParaAutuacao)).andExpect(status().isOk());
		
		//Realiza a devolução da petição.
		this.mockMvc.perform(post("/api/peticao/" + idPeticao + "/devolucao").contentType(MediaType.APPLICATION_JSON)
				.content(this.peticaoAutuadaParaDistribuicao)).andExpect(status().isOk()).andExpect(jsonPath("$.relator", is(36)));
	}
	*/
	/*
    @Test
    public void distribuir() throws Exception {
    	// Passo 01: Solicitando o Registro da Petição Física...
		mockMvc.perform(post("/api/peticao/fisica").contentType(MediaType.APPLICATION_JSON).content("{\"tipoRecebimento\":\"1\"}")).andExpect(status().isOk()).andExpect(content().string("4"));
		
		// Passo 02: Verificando se o processo de recebimento se encontra em "Pré-Autuação"...
		mockMvc.perform(get("/api/tarefas").header("papel", "recebedor")).andExpect(status().isOk()).andExpect(jsonPath("$[0].id", is("7"))).andExpect(jsonPath("$[0].descricao", is("Pré-Autuar Processo")));
        
		// Passo 03: Finalizando tarefa de pré-autuação da petição...
        mockMvc.perform(post("/api/peticao/7/preautuacao").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
     
		// Passo 04: Verificando se o processo de recebimento se encontra em "Autuação"...
		mockMvc.perform(get("/api/tarefas").header("papel", "autuador")).andExpect(status().isOk()).andExpect(jsonPath("$[0].id", is("13"))).andExpect(jsonPath("$[0].descricao", is("Autuar Processo")));
        
		// Passo 05: Finalizando tarefa de autuação com a petição válida...
        mockMvc.perform(post("/api/peticao/13/autuacao").contentType(MediaType.APPLICATION_JSON).content("{\"peticaoValida\":\"true\"}")).andExpect(status().isOk());
     
		// Passo 06: Verificando se o processo de recebimento se encontra em "Distribuição"...
		mockMvc.perform(get("/api/tarefas").header("papel", "distribuidor")).andExpect(status().isOk()).andExpect(jsonPath("$[0].id", is("17"))).andExpect(jsonPath("$[0].descricao", is("Distribuir Processo")));

		// Passo 07: Finalizando tarefa de distribuição...
        mockMvc.perform(post("/api/peticao/17/distribuicao").contentType(MediaType.APPLICATION_JSON).content("{\"relator\":\"Ministro\"}")).andExpect(status().isOk());
        
		// Verificação Final: Após a conclusão do processo, a lista de tarefas deve ser vazia.
		mockMvc.perform(get("/api/tarefas").header("papel", "recebedor")).andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.empty()));
    }

    @Test
    public void devolver() throws Exception {
    	// Passo 01: Solicitando o Registro da Remessa Física...
		mockMvc.perform(post("/api/peticao/fisica").contentType(MediaType.APPLICATION_JSON).content("{\"tipoRecebimento\":\"1\"}")).andExpect(status().isOk()).andExpect(content().string("20"));
		
		// Passo 02: Verificando se o processo de recebimento se encontra em "Pré-Autuação"...
		mockMvc.perform(get("/api/tarefas").header("papel", "recebedor")).andExpect(status().isOk()).andExpect(jsonPath("$[0].id", is("23"))).andExpect(jsonPath("$[0].descricao", is("Pré-Autuar Processo")));
        
		// Passo 03: Finalizando tarefa de pré-autuação da petição...
        mockMvc.perform(post("/api/peticao/23/preautuacao").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
     
		// Passo 04: Verificando se o processo de recebimento se encontra em "Autuação"...
		mockMvc.perform(get("/api/tarefas").header("papel", "autuador")).andExpect(status().isOk()).andExpect(jsonPath("$[0].id", is("29"))).andExpect(jsonPath("$[0].descricao", is("Autuar Processo")));
        
		// Passo 05: Finalizando tarefa de autuação com a petição inválida...
        mockMvc.perform(post("/api/peticao/29/autuacao").contentType(MediaType.APPLICATION_JSON).content("{\"peticaoValida\":\"false\"}")).andExpect(status().isOk());
        
		// Passo 06: Verificando se o processo de recebimento se encontra em "Devolução"...
		mockMvc.perform(get("/api/tarefas").header("papel", "devolvedor")).andExpect(status().isOk()).andExpect(jsonPath("$[0].id", is("33"))).andExpect(jsonPath("$[0].descricao", is("Devolver Processo")));
        
		// Passo 07: Finalizando tarefa de devolução da petição inválida...
        mockMvc.perform(post("/api/peticao/33/devolucao").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        
		// Verificação Final: Após a conclusão do processo, a lista de tarefas deve ser vazia.
		mockMvc.perform(get("/api/tarefas").header("papel", "recebedor")).andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.empty()));
    }
*/
}
