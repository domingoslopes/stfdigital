/**
 * 
 */
package br.jus.stf.processamentoinicial.suporte.interfaces;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import br.jus.stf.plataforma.shared.tests.AbstractIntegrationTests;

/**
 * Teste de integração da API REST de motivos de inaptidão de processos recusais.
 * 
 * @author Anderson.Araujo
 * 
 * @since 27.01.2016
 *
 */
public class MotivoInaptidaoIntegrationTests extends AbstractIntegrationTests {
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void listarMotivosInaptidao() throws Exception {
		mockMvc.perform(get("/api/motivos")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(8)));
	}

}
