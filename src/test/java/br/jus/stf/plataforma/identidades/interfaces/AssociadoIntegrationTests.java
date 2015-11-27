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
	
	private String dadosAssociadoParaCadastro;
	
	@Before
	public void criarObjetosJSON() throws UnsupportedEncodingException, Exception{
		
		StringBuilder dadosAssociadoParaCadastro = new StringBuilder();
		dadosAssociadoParaCadastro.append("{\"idOrgao\":1, \"cpf\":\"00011188545\",\"nome\":\"FULANO DA SILVA\",\"tipoAssociacao\":\"Gestor\",\"cargo\":\"Analista\"}");
		this.dadosAssociadoParaCadastro = dadosAssociadoParaCadastro.toString();
	}
	
	@Test
	public void cadastrarAssociado() throws Exception {
		super.mockMvc.perform(post("/api/associado/").header("papel", "Gestor-cadastro").contentType(MediaType.APPLICATION_JSON)
				.content(this.dadosAssociadoParaCadastro)).andExpect(status().isOk());
	}
}
