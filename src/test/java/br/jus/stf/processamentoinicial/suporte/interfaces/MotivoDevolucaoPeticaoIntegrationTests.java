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
 * Teste da API REST de motivos de devolução de petição.
 * 
 * @author Anderson.Araujo
 * @since 17.03.2016
 *
 */
public class MotivoDevolucaoPeticaoIntegrationTests extends AbstractIntegrationTests {
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void listarMotivosDevolucaoPeticao() throws Exception {
		mockMvc.perform(get("/api/motivos-devolucao")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(3)));
	}
}