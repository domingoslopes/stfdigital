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
		mockMvc.perform(get("/api/dashboards").header("login", "peticionador")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].dashlets[0].nome", is("minhas-peticoes")));

		mockMvc.perform(get("/api/dashboards").header("login", "preautuador-originario")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].dashlets[0].nome", is("minhas-tarefas")));
				
		mockMvc.perform(get("/api/dashboards").header("login", "autuador")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].dashlets[0].nome", is("minhas-tarefas")));

		mockMvc.perform(get("/api/dashboards").header("login", "distribuidor")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].dashlets[0].nome", is("minhas-tarefas")));

		mockMvc.perform(get("/api/dashboards").header("login", "recebedor")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].dashlets[0].nome", is("minhas-peticoes")));
		
		mockMvc.perform(get("/api/dashboards").header("login", "cartoraria")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].dashlets[0].nome", is("minhas-tarefas")));
		
		mockMvc.perform(get("/api/dashboards").header("login", "representante")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].dashlets[0].nome", is("minhas-peticoes")));
		
		mockMvc.perform(get("/api/dashboards").header("login", "gestor-autuacao")).andExpect(status().isOk())
				.andExpect(jsonPath("$[1].dashlets[2].nome", is("grafico-gestao")));
	}

}
