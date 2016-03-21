package br.jus.stf.processamentoinicial.distribuicao.interfaces;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;

import br.jus.stf.plataforma.shared.tests.AbstractIntegrationTests;

/**
 * Teste de integração da funcionalidade enviar processo.
 * 
 * @author Anderson.Araujo
 * @since 21.03.2016
 *
 */
public class EnviaProcessoIntegrationTests extends AbstractIntegrationTests {

	private String login;
	
	@Before
	public void onLoad(){
		login = "representante-tribunal";
	}
	
	@Test
	public void salvarProcessoParaEnvio() throws Exception{
		String urlServico = "/api/actions/salvar-processo/execute";
		String salvarProcessoCommand = gerarCommand();
		
		super.mockMvc.perform(
				post(urlServico).header("login", login).contentType(MediaType.APPLICATION_JSON).content(salvarProcessoCommand)
		).andExpect(status().isOk());
	}
	
	@Test
	public void enviarProcessoParaSTF() throws Exception{
		String urlServico = "/api/actions/enviar-processo/execute";
		String enviarProcessoCommand = gerarCommand();
		
		super.mockMvc.perform(
				post(urlServico).header("login", login).contentType(MediaType.APPLICATION_JSON).content(enviarProcessoCommand)
		).andExpect(status().isOk());
	}
	
	private String gerarCommand(){
		StringBuilder command = new StringBuilder();
		command.append("{\"resources\":[{\"classeId\":\"HC\", \"sigilo\": \"Público\", \"numeroRecursos\": 2, \"preferencias\": [1, 2], ");
		command.append("\"origens\": [{\"unidadeFederacaoId\": 1, \"codigoJuizoOrigem\": 1, \"numeroProcesso\": 1, \"numeroOrdem\": 1}], ");
		command.append("\"assuntoId\": \"864\", \"partesPoloAtivo\": [\"FULADO DA SILVA\"], \"partesPoloPassivo\": [\"BELTRANO LEMOS\"]");
		command.append("}]}");
		
		return command.toString();
	}
}
