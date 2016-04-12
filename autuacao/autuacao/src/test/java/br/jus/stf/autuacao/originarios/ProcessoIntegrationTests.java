package br.jus.stf.autuacao.originarios;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import br.jus.stf.core.framework.testing.IntegrationTestsSupport;

/**
 * Valida a API de autuação de originários.
 * 
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 17.02.2016
 */
@Ignore
@SpringApplicationConfiguration(ApplicationContextInitializer.class)
public class ProcessoIntegrationTests extends IntegrationTestsSupport {
    
    @Test
    public void naoDeveRegistrarUmaPeticaoInvalida() throws Exception {
        String processo = "{}";
        
        ResultActions result = mockMvc.perform(post("/api/processos/autuacao").contentType(APPLICATION_JSON).content(processo));
        
        result.andExpect(status().isBadRequest());
    }
    
}
