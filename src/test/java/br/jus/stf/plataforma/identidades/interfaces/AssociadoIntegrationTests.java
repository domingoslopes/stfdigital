package br.jus.stf.plataforma.identidades.interfaces;

import java.io.UnsupportedEncodingException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import br.jus.stf.plataforma.shared.tests.AbstractIntegrationTests;

/**
 * Realiza o teste de integração por meio da API Rest de associado.
 * 
 * @author Anderson.Araujo
 * 
 * @since 27.11.2015
 *
 */
public class AssociadoIntegrationTests extends AbstractIntegrationTests {
	
	private String dadosAssociadoComCargoParaCadastro;
	private String dadosAssociadoSemCargoParaCadastro;
	
	@Before
	public void criarObjetosJSON() throws UnsupportedEncodingException, Exception{
		
		StringBuilder dadosAssociadoComCargoParaCadastro = new StringBuilder();
		dadosAssociadoComCargoParaCadastro.append("{\"idOrgao\":1, \"cpf\":\"54218811806\",\"nome\":\"FULANO SAYAJIN\",\"tipoAssociacao\":\"Gestor\",\"cargo\":\"Analista\"}");
		this.dadosAssociadoComCargoParaCadastro = dadosAssociadoComCargoParaCadastro.toString();
		
		StringBuilder dadosAssociadoSemCargoParaCadastro = new StringBuilder();
		dadosAssociadoSemCargoParaCadastro.append("{\"idOrgao\":1, \"cpf\":\"55448211780\",\"nome\":\"CICRANO POLICARBONATO\",\"tipoAssociacao\":\"Representante\",\"cargo\":\"\"}");
		this.dadosAssociadoSemCargoParaCadastro = dadosAssociadoSemCargoParaCadastro.toString();
	}
	
	@Test
	public void cadastrarAssociadoComCargo() throws Exception {
		super.mockMvc.perform(post("/api/associado/").header("login", "gestor-cadastro").contentType(MediaType.APPLICATION_JSON)
				.content(this.dadosAssociadoComCargoParaCadastro)).andExpect(status().isOk());
	}
	
	@Test
	public void cadastrarAssociadoSemCargo() throws Exception {
		super.mockMvc.perform(post("/api/associado/").header("login", "gestor-cadastro").contentType(MediaType.APPLICATION_JSON)
				.content(this.dadosAssociadoSemCargoParaCadastro)).andExpect(status().isOk());
	}
}
