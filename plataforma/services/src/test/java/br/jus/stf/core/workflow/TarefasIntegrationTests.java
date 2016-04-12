package br.jus.stf.core.workflow;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.boot.test.SpringApplicationConfiguration;

import br.jus.stf.core.ApplicationContextInitializer;
import br.jus.stf.core.framework.testing.IntegrationTestsSupport;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 16.03.2016
 */
@Ignore
@SpringApplicationConfiguration(ApplicationContextInitializer.class)
public class TarefasIntegrationTests extends IntegrationTestsSupport {

    @Test
    public void deveRejeitarUmaPesquisaInvalida() throws Exception {
        mockMvc.perform(post("/api/processos").contentType(APPLICATION_JSON).content("{}")).andExpect(status().isBadRequest());
    }
    
}
