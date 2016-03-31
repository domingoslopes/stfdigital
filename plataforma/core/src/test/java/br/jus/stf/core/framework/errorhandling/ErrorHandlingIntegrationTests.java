package br.jus.stf.core.framework.errorhandling;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import br.jus.stf.core.framework.testing.IntegrationTestsSupport;

/**
 * Executa os testes necessários para validação do mecanismo de tratamento de exceções implementado 
 * pelo {@link GlobalControllerExceptionHandler}.
 * 
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 21.12.2015
 * 
 * @see ErrorMockRestResource
 */
@SpringApplicationConfiguration(ErrorMockContextInitializer.class)
public class ErrorHandlingIntegrationTests extends IntegrationTestsSupport {
    
    @Test
    public void deveRetornarUmErroDeServidor() throws Exception {
        ResultActions result = mockMvc.perform(post("/api/errors/500"));
        
        result.andExpect(status().is5xxServerError());
    }
    
    @Test
    public void deveRetorarUmErroDeRequisicaoInvalida() throws Exception {
        ResultActions result = mockMvc.perform(post("/api/errors/400"));
        
        result.andExpect(status().isBadRequest());
    }
    
    @Test
    public void naoDeveRetornarErros() throws Exception {
        ResultActions result = mockMvc.perform(post("/api/errors/200"));
        
        result.andExpect(status().isOk());
    }
    
}
