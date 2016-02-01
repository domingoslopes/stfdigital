package br.jus.stf.jurisprudencia.controletese.interfaces;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;

import br.jus.stf.plataforma.shared.tests.AbstractIntegrationTests;

/**
 * Teste de integração da API REST de teses.
 * 
 * @author Anderson.Araujo
 * 
 * @since 28.01.2016
 *
 */
public class TeseIntegrationTests extends AbstractIntegrationTests {
	
	private static final String TIPO_TESE_REPERCUSSAO_GERAL = "REPERCUSSAO_GERAL";
	private static final String TIPO_TESE_PRE_TEMA = "PRE_TEMA";
	private static final String TIPO_TESE_CONTROVERSIA = "CONTROVERSIA";
	private long idRepercussaoGeral;
	private long idPreTema;
	private long idControversia;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		idRepercussaoGeral = 1903L;
		idPreTema = 1516L;
		idControversia = 1781L;
	}
	
	@Test
	public void listarTesesPorTipoENumero() throws Exception {
		mockMvc.perform(get("/api/teses/").param("tipo", TIPO_TESE_REPERCUSSAO_GERAL).param("numero", "547")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)));
	}
	
	@Test
	public void testConsultarTesesPorId() throws Exception {
		String ids = String.format("[%d, %d, %d]", idRepercussaoGeral, idPreTema, idControversia);
		mockMvc.perform(post("/api/teses/ids").contentType(MediaType.APPLICATION_JSON).content(ids)).andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(3)))
			.andExpect(jsonPath("$[?(@.codigo == " + idRepercussaoGeral + ")].tipoTese[0]", is(TIPO_TESE_REPERCUSSAO_GERAL)))
			.andExpect(jsonPath("$[?(@.codigo == " + idPreTema + ")].tipoTese[0]", is(TIPO_TESE_PRE_TEMA)))
			.andExpect(jsonPath("$[?(@.codigo == " + idControversia + ")].tipoTese[0]", is(TIPO_TESE_CONTROVERSIA)));
	}
	
}
