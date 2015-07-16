package br.jus.stf.autuacao.interfaces;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.jus.stf.plataforma.ApplicationContextInitializer;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 17.06.2015
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationContextInitializer.class)
@WebAppConfiguration
@IntegrationTest
public class AutuacaoOriginariosIntegrationTests {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void distribuir() throws Exception {
    	// Passo 01: Solicitando o Registro da Petição Física...
		mockMvc.perform(post("/api/peticao/fisica").contentType(MediaType.APPLICATION_JSON).content("{\"tipoRecebimento\":\"1\"}")).andExpect(status().isOk()).andExpect(content().string("4"));
		
		// Passo 02: Verificando se o processo de recebimento se encontra em "Pré-Autuação"...
		mockMvc.perform(get("/api/tarefas/recebedores")).andExpect(status().isOk()).andExpect(jsonPath("$[0].id", is("7"))).andExpect(jsonPath("$[0].descricao", is("Pré-Autuar Processo")));
        
		// Passo 03: Finalizando tarefa de pré-autuação da petição...
        mockMvc.perform(post("/api/peticao/7/preautuacao").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
     
		// Passo 04: Verificando se o processo de recebimento se encontra em "Autuação"...
		mockMvc.perform(get("/api/tarefas/autuadores")).andExpect(status().isOk()).andExpect(jsonPath("$[0].id", is("12"))).andExpect(jsonPath("$[0].descricao", is("Autuar Processo")));
        
		// Passo 05: Finalizando tarefa de autuação com a petição válida...
        mockMvc.perform(post("/api/peticao/12/autuacao").contentType(MediaType.APPLICATION_JSON).content("{\"classificacao\":\"1\"}")).andExpect(status().isOk());
     
		// Passo 06: Verificando se o processo de recebimento se encontra em "Distribuição"...
		mockMvc.perform(get("/api/tarefas/distribuidores")).andExpect(status().isOk()).andExpect(jsonPath("$[0].id", is("15"))).andExpect(jsonPath("$[0].descricao", is("Distribuir Processo")));

		// Passo 07: Finalizando tarefa de distribuição...
        mockMvc.perform(post("/api/peticao/15/distribuicao").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        
		// Verificação Final: Após a conclusão do processo, a lista de tarefas deve ser vazia.
		mockMvc.perform(get("/api/tarefas")).andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.empty()));
    }

    @Test
    public void devolver() throws Exception {
    	// Passo 01: Solicitando o Registro da Remessa Física...
		mockMvc.perform(post("/api/peticao/fisica").contentType(MediaType.APPLICATION_JSON).content("{\"tipoRecebimento\":\"1\"}")).andExpect(status().isOk()).andExpect(content().string("17"));
		
		// Passo 02: Verificando se o processo de recebimento se encontra em "Pré-Autuação"...
		mockMvc.perform(get("/api/tarefas/recebedores")).andExpect(status().isOk()).andExpect(jsonPath("$[0].id", is("20"))).andExpect(jsonPath("$[0].descricao", is("Pré-Autuar Processo")));
        
		// Passo 03: Finalizando tarefa de pré-autuação da petição...
        mockMvc.perform(post("/api/peticao/20/preautuacao").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
     
		// Passo 04: Verificando se o processo de recebimento se encontra em "Autuação"...
		mockMvc.perform(get("/api/tarefas/autuadores")).andExpect(status().isOk()).andExpect(jsonPath("$[0].id", is("25"))).andExpect(jsonPath("$[0].descricao", is("Autuar Processo")));
        
		// Passo 05: Finalizando tarefa de autuação com a petição inválida...
        mockMvc.perform(post("/api/peticao/25/autuacao").contentType(MediaType.APPLICATION_JSON).content("{\"classificacao\":\"-1\"}")).andExpect(status().isOk());
        
		// Passo 06: Verificando se o processo de recebimento se encontra em "Devolução"...
		mockMvc.perform(get("/api/tarefas/autuadores")).andExpect(status().isOk()).andExpect(jsonPath("$[0].id", is("28"))).andExpect(jsonPath("$[0].descricao", is("Devolver Processo")));
        
		// Passo 07: Finalizando tarefa de devolução da petição inválida...
        mockMvc.perform(post("/api/peticao/28/devolucao").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        
		// Verificação Final: Após a conclusão do processo, a lista de tarefas deve ser vazia.
		mockMvc.perform(get("/api/tarefas")).andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.empty()));
    }

}
