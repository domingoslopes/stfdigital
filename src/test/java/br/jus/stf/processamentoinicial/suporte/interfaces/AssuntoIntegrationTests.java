package br.jus.stf.processamentoinicial.suporte.interfaces;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import br.jus.stf.plataforma.shared.tests.AbstractIntegrationTests;

/**
 * Teste de integração da API REST de assuntos.
 * 
 * @author Anderson.Araujo
 * 
 * @since 26.01.2016
 *
 */

public class AssuntoIntegrationTests extends AbstractIntegrationTests {
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void listarAssuntosPorDescricao() throws Exception {
		mockMvc.perform(get("/api/assuntos?termo=direito")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(4)));
	}
	
	@Test
	public void listarAssuntosPorCodigo() throws Exception {
		mockMvc.perform(get("/api/assuntos/864")).andExpect(status().isOk()).andExpect(jsonPath("$.codigo", is("864")));
	}
}
