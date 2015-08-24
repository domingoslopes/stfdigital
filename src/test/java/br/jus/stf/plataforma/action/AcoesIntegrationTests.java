package br.jus.stf.plataforma.action;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    	
    	mockMvc.perform(get("/api/actions")
    			.accept(MediaType.APPLICATION_JSON))
    		.andExpect(status().isOk())
    		.andDo(print());
    }
    
    @Test
    public void verificaAcoes() throws Exception {
    	
    	mockMvc.perform(post("/api/actions/isallowed")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content("{\"ids\":[\"dummy_action\"], \"resources\": [{\"attr\":\"TESTE1\"}]}"))
    		.andExpect(status().isOk())
    		.andExpect(jsonPath("$[0]", is("dummy_action")))
    		.andDo(print());
    }
    
    @Test
    public void verificaAcao() throws Exception {
    	
    	mockMvc.perform(post("/api/actions/dummy_action/isallowed")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content("{\"resources\": [{\"attr\":\"TESTE1\"}]}"))
    		.andExpect(status().isOk())
    		.andExpect(jsonPath("$", is(true)))
    		.andDo(print());
    }
    
    @Test
    public void executaAcao() throws Exception {
    	
    	mockMvc.perform(post("/api/actions/dummy_action/execute")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content("{\"resources\": [{\"attr\":\"TESTE1\"}, {\"attr\":\"TESTE2\"}]}"))
    		.andExpect(status().isOk())
    		.andDo(print());    	
    }
    
    @Test
    public void executaAcaoRestrita() throws Exception {

		setAuthenticationAuthorities("RESTRICT_ACTION");
    	mockMvc.perform(post("/api/actions/do_nothing_long/execute")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content("{\"resources\": [1]}"))
    		.andExpect(status().isOk())
    		.andDo(print());
    	setAuthenticationAuthorities(new String[] {});
    }
    
    @Test
    public void executaAcaoSemRecursos() throws Exception {
    	
    	mockMvc.perform(post("/api/actions/do_nothing/execute")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content("{\"resources\": []}"))
    		.andExpect(status().isOk())
    		.andDo(print());    	
    }
    
	private void setAuthenticationAuthorities(String... authorities) {
		Authentication authentication = new TestingAuthenticationToken("", "", authorities);
		authentication.setAuthenticated(true);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}
