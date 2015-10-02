package br.jus.stf.processamentoinicial.autuacao.interfaces.actions;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;

import org.apache.commons.io.IOUtils;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import br.jus.stf.plataforma.shared.tests.AbstractIntegrationTests;

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
		peticaoAutuadaParaDistribuicao.append("[{\"peticaoId\": @,");
		peticaoAutuadaParaDistribuicao.append("\"ministroId\":36}]}");
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
		peticaoEletronica.append("{\"resources\": [{\"classeId\":\"HC\",");
		peticaoEletronica.append("\"partesPoloAtivo\":[1, 2],");
		peticaoEletronica.append("\"partesPoloPassivo\":[3, 4],");
		peticaoEletronica.append("\"pecas\": [{\"documentoTemporario\":\"" + idDoc + "\",");
		peticaoEletronica.append("\"tipo\":1,");
		peticaoEletronica.append("\"descricao\":\"Petição inicial\"}]}]}");
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
		peticaoFisicaParaPreautuacao.append("\"classeId\":\"ADI\"}]}");
		this.peticaoFisicaParaPreautuacao = peticaoFisicaParaPreautuacao.toString();
		
		//Cria um objeto para ser usado no processo de rejeição de uma petição.
		StringBuilder peticaoInValidaParaAutuacao =  new StringBuilder();
		peticaoInValidaParaAutuacao.append("{\"resources\": ");
		peticaoInValidaParaAutuacao.append("[{\"peticaoId\": @,");
		peticaoInValidaParaAutuacao.append("\"classeId\":\"ADI\",");
		peticaoInValidaParaAutuacao.append("\"valida\":false,");
		peticaoInValidaParaAutuacao.append("\"motivo\":\"Petição inválida\"}]}");
		this.peticaoInvalidaParaAutuacao = peticaoInValidaParaAutuacao.toString();
	}
	
	private void setAuthenticationAuthorities(String... authorities) {
		Authentication authentication = new TestingAuthenticationToken("", "", authorities);
		authentication.setAuthenticated(true);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
	
    @Test
    public void executarAcaoDistribuirPeticaoEletronica() throws Exception {
    	
    	String peticaoId = "";
    	setAuthenticationAuthorities("peticionador");
    	
    	//Envia a petição eletrônica.
    	peticaoId = super.mockMvc.perform(post("/api/actions/registrar_peticao_eletronica/execute").contentType(MediaType.APPLICATION_JSON)
    		.content(this.peticaoEletronica)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		//Recupera a(s) tarefa(s) do autuador.
		super.mockMvc.perform(get("/api/workflow/tarefas").header("papel", "autuador")).andExpect(status().isOk())
			.andExpect(jsonPath("$[0].descricao", is("Autuar Processo")));
		
		setAuthenticationAuthorities("autuador");
		
		//Realiza a autuação.
		super.mockMvc.perform(post("/api/actions/autuar_peticao/execute").contentType(MediaType.APPLICATION_JSON)
			.content(this.peticaoValidaParaAutuacao.replace("@", peticaoId))).andExpect(status().isOk());
		
		//Recupera a(s) tarefa(s) do distribuidor.
		super.mockMvc.perform(get("/api/workflow/tarefas").header("papel", "distribuidor")).andExpect(status().isOk())
			.andExpect(jsonPath("$[0].descricao", is("Distribuir Processo")));
		
		setAuthenticationAuthorities("distribuidor");
		
		//Realiza a distribuição.
		super.mockMvc.perform(post("/api/actions/distribuir_peticao/execute").contentType(MediaType.APPLICATION_JSON)
			.content(this.peticaoAutuadaParaDistribuicao.replace("@", peticaoId))).andExpect(status().isOk()).andExpect(jsonPath("$.relator", is(36)));
		
		//Tenta recuperar as tarefas do autuador. A ideia é receber uma lista vazia, já que a instância do processo foi encerrada.
		super.mockMvc.perform(get("/api/workflow/tarefas").header("papel", "autuador")).andExpect(status().isOk())
			.andExpect(jsonPath("$", Matchers.empty()));
		
		setAuthenticationAuthorities(new String[] {});
    }
	
    @Test
    public void executarAcaoRegistroPeticaoFisica() throws Exception {
    	
    	String peticaoId = "";
    	setAuthenticationAuthorities("recebedor");
    	
    	//Envia a petição eletrônica.
    	peticaoId = super.mockMvc.perform(post("/api/actions/registrar_peticao_fisica/execute").contentType(MediaType.APPLICATION_JSON)
    		.content(this.peticaoFisicaParaRegistro)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
    	//Recupera a(s) tarefa(s) do préautuador.
		super.mockMvc.perform(get("/api/workflow/tarefas").header("papel", "pre-autuador")).andExpect(status().isOk())
			.andExpect(jsonPath("$[0].descricao", is("Pré-Autuar Processo")));
    	
		setAuthenticationAuthorities("preautuador");
		
		//Realiza a préautuação da petição física.
		super.mockMvc.perform(post("/api/actions/preautuar_peticao_fisica/execute").contentType(MediaType.APPLICATION_JSON)
	    		.content(this.peticaoFisicaParaPreautuacao.replace("@", peticaoId))).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		//Recupera a(s) tarefa(s) do autuador.
		super.mockMvc.perform(get("/api/workflow/tarefas").header("papel", "autuador")).andExpect(status().isOk())
			.andExpect(jsonPath("$[0].descricao", is("Autuar Processo")));
		
		setAuthenticationAuthorities("autuador");
		
		//Realiza a autuação.
		super.mockMvc.perform(post("/api/actions/autuar_peticao/execute").contentType(MediaType.APPLICATION_JSON)
			.content(this.peticaoValidaParaAutuacao.replace("@", peticaoId))).andExpect(status().isOk());
		
		//Recupera a(s) tarefa(s) do distribuidor.
		super.mockMvc.perform(get("/api/workflow/tarefas").header("papel", "distribuidor")).andExpect(status().isOk())
			.andExpect(jsonPath("$[0].descricao", is("Distribuir Processo")));
		
		setAuthenticationAuthorities("distribuidor");
		
		//Realiza a distribuição.
		super.mockMvc.perform(post("/api/actions/distribuir_peticao/execute").contentType(MediaType.APPLICATION_JSON)
			.content(this.peticaoAutuadaParaDistribuicao.replace("@", peticaoId))).andExpect(status().isOk()).andExpect(jsonPath("$.relator", is(36)));
		
		//Tenta recuperar as tarefas do autuador. A ideia é receber uma lista vazia, já que a instância do processo foi encerrada.
		super.mockMvc.perform(get("/api/workflow/tarefas").header("papel", "autuador")).andExpect(status().isOk())
			.andExpect(jsonPath("$", Matchers.empty()));
		
		setAuthenticationAuthorities(new String[] {});
    }
    
    @Test
    public void executarAcaoRejeitarPeticao() throws Exception {
    	
    	String peticaoId = "";
    	setAuthenticationAuthorities("peticionador");
    	
    	//Envia a petição eletrônica.
    	peticaoId = super.mockMvc.perform(post("/api/actions/registrar_peticao_eletronica/execute").contentType(MediaType.APPLICATION_JSON)
    		.content(this.peticaoEletronica)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		//Recupera a(s) tarefa(s) do autuador.
		super.mockMvc.perform(get("/api/workflow/tarefas").header("papel", "autuador")).andExpect(status().isOk())
			.andExpect(jsonPath("$[0].descricao", is("Autuar Processo")));
		
		setAuthenticationAuthorities("autuador");
		
		//Realiza a autuação.
		super.mockMvc.perform(post("/api/actions/autuar_peticao/execute").contentType(MediaType.APPLICATION_JSON)
			.content(this.peticaoInvalidaParaAutuacao.replace("@", peticaoId))).andExpect(status().isOk());
		
		//Tenta recuperar as tarefas do autuador. A ideia é receber uma lista vazia, já que a instância do processo foi encerrada.
		super.mockMvc.perform(get("/api/workflow/tarefas").header("papel", "autuador")).andExpect(status().isOk())
			.andExpect(jsonPath("$", Matchers.empty()));
		
		setAuthenticationAuthorities(new String[] {});
    }
}
