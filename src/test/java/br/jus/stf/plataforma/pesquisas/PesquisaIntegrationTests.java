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
<<<<<<< HEAD
		
=======

>>>>>>> 5c832ee459cec621020caaa6b8c4ded9f4da3eda
		mockMvc.perform(post("/api/pesquisas").contentType(MediaType.APPLICATION_JSON).content("{\"indices\": [\"teste-distribuicao\"], \"filtros\": {\"classe.sigla\": [\"HC\"]}, \"campos\": [\"classe.sigla\"] }"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].tipo", is("Processo")))
 				.andExpect(jsonPath("$[0].objeto['classe.sigla']", is("HC")));
		
		elasticsearchTemplate.deleteIndex("teste-distribuicao");
	}

	@Test
	public void pesquisarComAgregacao() throws Exception {
		
		String jsonProcessoAutuado1 = "{\"identificadorPeticao\":\"123/2015\",\"classeProcessual\":\"ADI\",\"mesAutuacao\":5}";
		String jsonProcessoAutuado2 = "{\"identificadorPeticao\":\"456/2015\",\"classeProcessual\":\"ADI\",\"mesAutuacao\":5}";
		String jsonProcessoAutuado3 = "{\"identificadorPeticao\":\"789/2015\",\"classeProcessual\":\"HC\",\"mesAutuacao\":5}";

		IndexQuery query1 = new IndexQueryBuilder()
				.withIndexName("quantidade-autuacoes")
				.withType("QuantidadeAtuacoes")
				.withSource(jsonProcessoAutuado1)
				.withId("123")
				.build();
		elasticsearchTemplate.index(query1);
		elasticsearchTemplate.refresh("quantidade-autuacoes", true);
		
		IndexQuery query2 = new IndexQueryBuilder()
		.withIndexName("quantidade-autuacoes")
			.withType("QuantidadeAtuacoes")
			.withSource(jsonProcessoAutuado2)
			.withId("456")
			.build();
		elasticsearchTemplate.index(query2);
		elasticsearchTemplate.refresh("quantidade-autuacoes", true);
		
		IndexQuery query3 = new IndexQueryBuilder()
		.withIndexName("quantidade-autuacoes")
			.withType("QuantidadeAtuacoes")
			.withSource(jsonProcessoAutuado3)
			.withId("789")
			.build();
		elasticsearchTemplate.index(query3);
		elasticsearchTemplate.refresh("quantidade-autuacoes", true);
		
		mockMvc.perform(post("/api/pesquisas").contentType(MediaType.APPLICATION_JSON).content("{\"indices\": [\"quantidade-autuacoes\"], \"filtros\": {\"mesAutuacao\": [5]}, \"campos\": [\"classeProcessual\"], \"campoAgregacao\": \"classeProcessual\" }"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].tipo", is("ValoresAgregados")))
			.andExpect(jsonPath("$[0].objeto['ADI']", is(2)))
			.andExpect(jsonPath("$[1].objeto['HC']", is(1)));

		elasticsearchTemplate.deleteIndex("quantidade-autuacoes");
	}
}
