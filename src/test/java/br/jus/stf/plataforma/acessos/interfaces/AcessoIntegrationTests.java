package br.jus.stf.plataforma.acessos.interfaces;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;

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
	
	@Test
	public void recuperarInformacoesUsuarioLogado() throws Exception{
		
		//Recupera as informações do usuário.
		this.mockMvc.perform(get("/api/acessos/usuario").header("login", "autuador")).andExpect(status().isOk())
			.andExpect(jsonPath("$.nome", is("autuador")));
	}
}
