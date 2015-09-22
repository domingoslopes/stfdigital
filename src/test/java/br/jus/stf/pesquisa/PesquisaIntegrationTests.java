package br.jus.stf.pesquisa;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.http.MediaType;

import br.jus.stf.AbstractIntegrationTests;
import br.jus.stf.autuacao.application.PeticaoApplicationService;
import br.jus.stf.autuacao.domain.model.FormaRecebimento;
import br.jus.stf.autuacao.domain.model.PartePeticao;
import br.jus.stf.autuacao.domain.model.PeticaoFactory;
import br.jus.stf.autuacao.domain.model.PeticaoFisica;
import br.jus.stf.autuacao.domain.model.TipoPolo;
import br.jus.stf.shared.domain.model.ClasseId;
import br.jus.stf.shared.domain.model.DocumentoId;
import br.jus.stf.shared.domain.model.MinistroId;
import br.jus.stf.shared.domain.model.PessoaId;

/**
 * @author Lucas.Rodrigues
 *
 */
public class PesquisaIntegrationTests extends AbstractIntegrationTests {
	
	@Autowired
	private PeticaoFactory peticaoFactory;
	
	@Autowired
	private PeticaoApplicationService peticaoApplicationService;
	
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Test
	public void pesquisar() throws Exception {
		
		//for (int i = 0; i < 20; i++) {
			PeticaoFisica peticao = peticaoApplicationService.registrar(1, 1, FormaRecebimento.SEDEX, "123");
			peticao.preautuar(new ClasseId("HC"));
			peticao.aceitar(new ClasseId("HC"));
			peticao.adicionarParte(new PartePeticao(new PessoaId(1L), TipoPolo.POLO_ATIVO));
			peticao.adicionarDocumento(new DocumentoId(1L));
			peticaoApplicationService.distribuir(peticao, new MinistroId(1L));
		//}
		elasticsearchTemplate.refresh("autuacao", true);
		
		mockMvc.perform(post("/api/pesquisa")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content("{\"indices\": [\"autuacao\"], \"filtros\": {\"identificacao\": \"HC\"}, \"campos\": [\"identificacao\", \"relator.codigo\"], \"ordenador\": \"identificacao\" }"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].tipo", is("Processo")));
			//.andDo(MockMvcResultHandlers.print());

	}
	
}
