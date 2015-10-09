package br.jus.stf.plataforma.dashboards;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;

import br.jus.stf.plataforma.shared.tests.AbstractIntegrationTests;

/**
 * Testes de integração do mecanismo de Dashboard.
 * 
 * @author Tomas.Godoi
 *
 */
public class DashboardIntegrationTests extends AbstractIntegrationTests {

	@Test
	public void recuperarDashboardPadrao() throws Exception {
		mockMvc.perform(get("/api/dashboards/padrao").header("papel", "peticionador")).andExpect(status().isOk())
				.andExpect(jsonPath("$.dashlets[0]", is("minhas-tarefas")))
				.andExpect(jsonPath("$.dashlets[1]", is("minhas-peticoes")));

		mockMvc.perform(get("/api/dashboards/padrao").header("papel", "preautuador")).andExpect(status().isOk())
				.andExpect(jsonPath("$.dashlets[0]", is("peticoes-para-preautuar")));

		mockMvc.perform(get("/api/dashboards/padrao").header("papel", "autuador")).andExpect(status().isOk())
				.andExpect(jsonPath("$.dashlets[0]", is("minhas-tarefas")));

		mockMvc.perform(get("/api/dashboards/padrao").header("papel", "distribuidor")).andExpect(status().isOk())
				.andExpect(jsonPath("$.dashlets[0]", is("grafico-distribuicao")))
				.andExpect(jsonPath("$.dashlets[1]", is("grafico-peticoes")));

		mockMvc.perform(get("/api/dashboards/padrao").header("papel", "recebedor")).andExpect(status().isOk())
				.andExpect(jsonPath("$.dashlets[0]", is("grafico-distribuicao")))
				.andExpect(jsonPath("$.dashlets[1]", is("minhas-peticoes")))
				.andExpect(jsonPath("$.dashlets[2]", is("grafico-peticoes")))
				.andExpect(jsonPath("$.dashlets[3]", is("peticoes-para-preautuar")));
	}

}
