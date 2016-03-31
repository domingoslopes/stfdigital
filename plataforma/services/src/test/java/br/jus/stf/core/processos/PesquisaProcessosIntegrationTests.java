package br.jus.stf.core.processos;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.boot.test.SpringApplicationConfiguration;

import br.jus.stf.core.ApplicationContextInitializer;
import br.jus.stf.core.framework.testing.IntegrationTestsSupport;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 22.03.2016
 */
@SpringApplicationConfiguration(ApplicationContextInitializer.class)
public class PesquisaProcessosIntegrationTests extends IntegrationTestsSupport {

    @Test
    public void deveRetornarTarefasPendentes() throws Exception {
        mockMvc.perform(get("/api/tarefas").contentType(APPLICATION_JSON)).andExpect(status().isOk());
    }
    
}
