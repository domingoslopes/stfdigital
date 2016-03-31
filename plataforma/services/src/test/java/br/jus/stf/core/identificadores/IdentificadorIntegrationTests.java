package br.jus.stf.core.identificadores;

import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import br.jus.stf.core.ApplicationContextInitializer;
import br.jus.stf.core.framework.testing.IntegrationTestsSupport;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 25.03.2016
 */
@SpringApplicationConfiguration(classes = ApplicationContextInitializer.class)
public class IdentificadorIntegrationTests extends IntegrationTestsSupport {

    @Test
    public void deveRetornarUmIdentificadorCategorizado() throws Exception {
    	ResultActions actions = mockMvc.perform(get("/api/identificadores").param("categoria", "ADI").contentType(APPLICATION_JSON)).andExpect(status().isOk());
    	
    	actions.andDo(MockMvcResultHandlers.print());
    	
        actions.andExpect((jsonPath("$.categoria", is("ADI"))));
        actions.andExpect((jsonPath("$.numero", is(1))));
    }
    
    @Test
    public void deveRetornarUmIdentificador() throws Exception {
    	ResultActions actions = mockMvc.perform(get("/api/identificadores").contentType(APPLICATION_JSON)).andExpect(status().isOk());
    	
    	actions.andDo(MockMvcResultHandlers.print());
    	
        actions.andExpect((jsonPath("$", is(1))));
    }
    
}
