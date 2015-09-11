package br.jus.stf.autuacao.interfaces;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import br.jus.stf.AbstractIntegrationTests;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 17.06.2015
 */

public class AutuacaoOriginariosIntegrationTests extends AbstractIntegrationTests {

	@Test
	public void enviarPeticaoEletronica() throws Exception {
		
		StringBuilder peticaoEletronica =  new StringBuilder();
		peticaoEletronica.append("{\"classe\":\"HC\",");
		peticaoEletronica.append("\"partesPoloAtivo\":[1, 2],");
		peticaoEletronica.append("\"partesPoloPassivo\":[3, 4],");
		peticaoEletronica.append("\"documentos\":[5, 6, 7]}");
		
		MvcResult resultado = this.mockMvc.perform(
			post("/api/peticao/")
			.contentType(MediaType.APPLICATION_JSON)
			.content(peticaoEletronica.toString()))
			.andExpect(status().isOk())
			.andReturn();
		
		String s = resultado.toString();
	}
	
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
