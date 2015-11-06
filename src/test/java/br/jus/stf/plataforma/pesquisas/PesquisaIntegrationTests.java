package br.jus.stf.plataforma.pesquisas;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import br.jus.stf.plataforma.shared.tests.AbstractIntegrationTests;

/**
 * @author Lucas Rodrigues
 */
public class PesquisaIntegrationTests extends AbstractIntegrationTests {

	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;

	@PersistenceContext
	private EntityManager entityManager;
	
	@Before
	public void setUp() {
		Authentication auth = Mockito.mock(Authentication.class);
		
		Mockito.when(auth.getPrincipal()).thenReturn("PETICIONADOR");
		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	@Test
	public void pesquisar() throws Exception {
		String jsonProcesso = "{\"id\":{\"sequencial\":123},\"classe\":{\"sigla\":\"HC\"},\"numero\":123456,\"identificacao\":\"HC123456\"}";

		IndexQuery query = new IndexQueryBuilder()
				.withIndexName("teste-distribuicao")
				.withType("Processo")
				.withSource(jsonProcesso)
				.withId("123")
				.build();
		elasticsearchTemplate.index(query);
		
		elasticsearchTemplate.refresh("teste-distribuicao", true);

		mockMvc.perform(post("/api/pesquisas").contentType(MediaType.APPLICATION_JSON).content("{\"indices\": [\"teste-distribuicao\"], \"filtros\": {\"classe.sigla\": \"HC\"}, \"campos\": [\"classe.sigla\"] }"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].tipo", is("Processo")))
				.andExpect(jsonPath("$[0].objeto['classe.sigla']", is("HC")));
		
		elasticsearchTemplate.deleteIndex("teste-distribuicao");
	}

}
