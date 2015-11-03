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
				.andExpect(jsonPath("$.dashlets[0]", is("minhas-tarefas")));

		mockMvc.perform(get("/api/dashboards/padrao").header("papel", "preautuador")).andExpect(status().isOk())
				.andExpect(jsonPath("$.dashlets[0]", is("minhas-tarefas")));

		mockMvc.perform(get("/api/dashboards/padrao").header("papel", "autuador")).andExpect(status().isOk())
				.andExpect(jsonPath("$.dashlets[0]", is("minhas-tarefas")));

		mockMvc.perform(get("/api/dashboards/padrao").header("papel", "distribuidor")).andExpect(status().isOk())
				.andExpect(jsonPath("$.dashlets[0]", is("minhas-tarefas")));

		mockMvc.perform(get("/api/dashboards/padrao").header("papel", "recebedor")).andExpect(status().isOk())
				.andExpect(jsonPath("$.dashlets[0]", is("minhas-tarefas")));
		
		mockMvc.perform(get("/api/dashboards/padrao").header("papel", "cartoraria")).andExpect(status().isOk())
		.andExpect(jsonPath("$.dashlets[0]", is("minhas-tarefas")));
		
		mockMvc.perform(get("/api/dashboards/padrao").header("papel", "representante")).andExpect(status().isOk())
		.andExpect(jsonPath("$.dashlets[0]", is("minhas-tarefas")));
		
		mockMvc.perform(get("/api/dashboards/padrao").header("papel", "gestor-autuacao")).andExpect(status().isOk())
		.andExpect(jsonPath("$.dashlets[0]", is("dashlet-grafico")));
	}

}
