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
		mockMvc.perform(get("/api/dashboards/padrao").header("login", "peticionador")).andExpect(status().isOk())
				.andExpect(jsonPath("$.dashlets[0]", is("minhas-peticoes")));

		mockMvc.perform(get("/api/dashboards/padrao").header("login", "preautuador-originario")).andExpect(status().isOk())
				.andExpect(jsonPath("$.dashlets[0]", is("minhas-tarefas")));

		mockMvc.perform(get("/api/dashboards/padrao").header("login", "autuador")).andExpect(status().isOk())
				.andExpect(jsonPath("$.dashlets[0]", is("minhas-tarefas")));

		mockMvc.perform(get("/api/dashboards/padrao").header("login", "distribuidor")).andExpect(status().isOk())
				.andExpect(jsonPath("$.dashlets[0]", is("minhas-tarefas")));

		mockMvc.perform(get("/api/dashboards/padrao").header("login", "recebedor")).andExpect(status().isOk())
				.andExpect(jsonPath("$.dashlets[0]", is("minhas-peticoes")));
		
		mockMvc.perform(get("/api/dashboards/padrao").header("login", "cartoraria")).andExpect(status().isOk())
		.andExpect(jsonPath("$.dashlets[0]", is("minhas-tarefas")));
		
		mockMvc.perform(get("/api/dashboards/padrao").header("login", "representante")).andExpect(status().isOk())
		.andExpect(jsonPath("$.dashlets[0]", is("minhas-peticoes")));
		
		mockMvc.perform(get("/api/dashboards/padrao").header("login", "gestor-autuacao")).andExpect(status().isOk())
		.andExpect(jsonPath("$.dashlets[0]", is("grafico-gestao")));
	}

}
