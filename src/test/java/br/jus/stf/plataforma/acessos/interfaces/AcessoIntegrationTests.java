package br.jus.stf.plataforma.acessos.interfaces;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;

import br.jus.stf.plataforma.shared.tests.AbstractIntegrationTests;

/**
 * Classe responsável pelos testes de integração da API de acessos da Plataforma.
 * 
 * @author Anderson.Araujo
 * 
 * @since 30.11.2015
 *
 */
public class AcessoIntegrationTests extends AbstractIntegrationTests {
	
	private StringBuilder permissoesUsuario;
	
	@Before
	public void carregarDadosTeste() {
		this.permissoesUsuario = new StringBuilder();
		this.permissoesUsuario.append("{\"idUsuario\":5,");
		this.permissoesUsuario.append("\"papeisAdicionados\":[1,2,3,4],");
		this.permissoesUsuario.append("\"gruposAdicionados\":[2],");
		this.permissoesUsuario.append("\"papeisRemovidos\":[5],");
		this.permissoesUsuario.append("\"gruposRemovidos\":[1,2]}");
	}
	
	
	@Test
	public void recuperarInformacoesUsuarioLogado() throws Exception{
		
		//Recupera as informações do usuário.
		this.mockMvc.perform(get("/api/acessos/usuario").header("login", "autuador")).andExpect(status().isOk())
			.andExpect(jsonPath("$.login", is("autuador")))
			.andExpect(jsonPath("$.nome", is("autuador")))
			.andExpect(jsonPath("$.setorLotacao", is("SEJ")))
			.andExpect(jsonPath("$.papeis[0].nome", is("autuador")))
			.andExpect(jsonPath("$.papeis[0].setor", is("SEJ")));
	}
	
	@Test
	public void carregarGruposUsuario() throws Exception {
		//Recupera as informações do usuário.
		this.mockMvc.perform(get("/api/acessos/usuarios/grupos")
			.header("login", "gestor-autuacao")
			.param("login", "gestor-autuacao")).andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(2)));
	}
	
	@Test
	public void carregarPapeisUsuario() throws Exception {
		//Recupera as informações do usuário.
		this.mockMvc.perform(get("/api/acessos/usuarios/papeis")
			.header("login", "autuador")
			.param("login", "autuador")).andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(1)));
	}
	
	@Test
	public void configurarPermissoesUsuario() throws Exception {
		//Realiza a autuação.
		this.mockMvc.perform(post("/api/acessos/permissoes/configuracao").contentType(MediaType.APPLICATION_JSON)
			.content(this.permissoesUsuario.toString())).andExpect(status().isOk());
	}
}