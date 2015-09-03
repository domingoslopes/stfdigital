package br.jus.stf.autuacao.interfaces.actions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import br.jus.stf.AbstractIntegrationTests;

public class ActionIntegrationTests extends AbstractIntegrationTests {
	
	private void setAuthenticationAuthorities(String... authorities) {
		Authentication authentication = new TestingAuthenticationToken("", "", authorities);
		authentication.setAuthenticated(true);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
	
    @Test
    public void executaAcaoPeticao() throws Exception {
    	setAuthenticationAuthorities("peticionador");
    	mockMvc.perform(post("/api/actions/registrar_peticao/execute")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content("{\"resources\": [{\"classe\":\"HC\",\"partesPoloAtivo\":[\"Fulano\"],\"partesPoloPassivo\":[\"Cicrano\"],\"documentos\":[\"id01\",\"id02\"]}]}"))
    		.andExpect(status().isOk());
    	setAuthenticationAuthorities(new String[] {});
    }
}
