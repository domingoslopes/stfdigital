package br.jus.stf.processamentoinicial.suporte.interfaces;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;

import br.jus.stf.plataforma.shared.tests.AbstractIntegrationTests;

/**
 * Teste de integração do serviço de tribunal/juízo.
 * 
 * @author Anderson.Araujo
 * @since 21.03.2016
 *
 */
public class TribunalJuizoIntegrationTests extends AbstractIntegrationTests {
	@Test
	public void listarTribunaisJuizos() throws Exception {
		mockMvc.perform(get("/api/tribunais-juizos")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(19)));
	}
}
