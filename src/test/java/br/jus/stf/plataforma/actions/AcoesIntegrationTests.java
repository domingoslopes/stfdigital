package br.jus.stf.plataforma.actions;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.http.MediaType;

import br.jus.stf.plataforma.shared.tests.AbstractIntegrationTests;

/**
 * @author Lucas.Rodrigues
 *
 */
public class AcoesIntegrationTests extends AbstractIntegrationTests {
	
    @Test
    public void listarAcoes() throws Exception {
    	
    	mockMvc.perform(get("/api/actions")
    			.header("login", "peticionador")
    			.accept(MediaType.APPLICATION_JSON))
    		.andExpect(status().isOk());
    		//.andDo(print());
    }
    
    @Test
    public void verificaAcoes() throws Exception {
    	
    	mockMvc.perform(post("/api/actions/isallowed")
    			.header("login", "peticionador")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content("{\"ids\":[\"dummy_action\"], \"resources\": [{\"attr\":\"TESTE1\"}]}"))
    		.andExpect(status().isOk())
    		.andExpect(jsonPath("$[0]", is("dummy_action")));
    		//.andDo(print());
    }
    
    @Test
    public void verificaAcao() throws Exception {
    	
    	mockMvc.perform(post("/api/actions/dummy_action/isallowed")
    			.header("login", "peticionador")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content("{\"resources\": [{\"attr\":\"TESTE1\"}]}"))
    		.andExpect(status().isOk())
    		.andExpect(jsonPath("$", is(true)));
    		//.andDo(print());
    	
    	mockMvc.perform(get("/api/actions/do_nothing/isallowed"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", is(true)));
    }
    
    @Test
    public void executaAcao() throws Exception {
    	
    	mockMvc.perform(post("/api/actions/dummy_action/execute")
    			.header("login", "peticionador")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content("{\"resources\": [{\"attr\":\"TESTE1\"}, {\"attr\":\"TESTE2\"}]}"))
    		.andExpect(status().isOk());
    		//.andDo(print());    	
    }
    
    @Test
    public void executaAcaoRestrita() throws Exception {

    	mockMvc.perform(post("/api/actions/do_nothing_long/execute")
    			.header("login", "peticionador")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content("{\"resources\": [1]}"))
    		.andExpect(status().isOk());
    		//.andDo(print());
    }
    
    @Test
    public void executaAcaoSemRecursos() throws Exception {
    	
    	mockMvc.perform(post("/api/actions/do_nothing/execute")
    			.header("login", "peticionador")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content("{\"resources\": [1]}"))
    		.andExpect(status().isOk());
    		//.andDo(print());
    	
    	mockMvc.perform(get("/api/actions/do_nothing/execute")
    			.header("login", "peticionador"))
    		.andExpect(status().isOk());
    }
    
}
