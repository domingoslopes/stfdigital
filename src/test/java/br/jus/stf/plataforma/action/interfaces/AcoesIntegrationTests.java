package br.jus.stf.plataforma.action.interfaces;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import br.jus.stf.AbstractIntegrationTests;

/**
 * @author Lucas.Rodrigues
 *
 */
public class AcoesIntegrationTests extends AbstractIntegrationTests {
    
    @Test
    public void listarAcoes() throws Exception {
    	
    	mockMvc.perform(post("/api/acoes/listar")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content("{\"resourcesType\":\"String\", \"resources\": []}"))
    		.andExpect(status().isOk())
    		.andExpect(jsonPath("$[0].id", is("DO_NOTHING")))
    		.andExpect(jsonPath("$[0].description", is("Do Nothing")))
    		.andDo(print());
    }
    
    @Test
    public void listarAcoesComRequiresResources() throws Exception {
    	
    	mockMvc.perform(post("/api/acoes/listar")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content("{\"context\":\"Dummy\", \"resourcesType\":\"DummyObj\", \"resources\": [{\"attr\":\"teste1\"}]}"))
    		.andExpect(status().isOk())
    		.andExpect(jsonPath("$[0].id", is("DO_NOTHING_REQUIRES")))
    		.andExpect(jsonPath("$[0].description", is("Do Nothing Requires")))
    		.andDo(print());
    }
    
    @Test
    public void listarAcoesComRestrict() throws Exception {

		setAuthenticationAuthorities("RESTRICT_ACTION");
    	mockMvc.perform(post("/api/acoes/listar")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content("{\"context\":\"Dummy\", \"resourcesType\":\"String\", \"resources\": [1]}"))
    		.andExpect(status().isOk())
    		.andExpect(jsonPath("$[0].id", is("DO_NOTHING_RESTRICT")))
    		.andExpect(jsonPath("$[0].description", is("Do Nothing Restrict")))
    		.andDo(print());
    	setAuthenticationAuthorities("ANOTHER_ACTION");
    }
    
    @Test
    public void listarAcoesComInteger() throws Exception {

    	mockMvc.perform(post("/api/acoes/listar")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content("{\"context\":\"Dummy\", \"resourcesType\":\"Integer\", \"resources\": [1, 2]}"))
    		.andExpect(status().isOk())
    		.andExpect(jsonPath("$[0].id", is("DO_NOTHING_INTEGER")))
    		.andExpect(jsonPath("$[0].description", is("Do Nothing Integer")))
    		.andDo(print());
    }
    
    @Test
    public void executaAcao() throws Exception {
    	
    	mockMvc.perform(post("/api/acoes/executar")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content("{\"actionId\":\"DO_NOTHING_REQUIRES\", \"resources\": [{\"attr\":\"teste1\"}]}"))
    		.andExpect(status().isOk())
    		.andDo(print());    	
    }
    
	private void setAuthenticationAuthorities(String... authorities) {
		Authentication authentication = new TestingAuthenticationToken("", "", authorities);
		authentication.setAuthenticated(true);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}
