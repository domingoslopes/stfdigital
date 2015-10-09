package br.jus.stf.plataforma.pesquisas;

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

import br.jus.stf.plataforma.shared.tests.AbstractIntegrationTests;
import br.jus.stf.processamentoinicial.autuacao.application.PeticaoApplicationService;
import br.jus.stf.processamentoinicial.autuacao.domain.model.FormaRecebimento;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PartePeticao;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PecaPeticao;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoFactory;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoFisica;
import br.jus.stf.processamentoinicial.autuacao.domain.model.TipoPeca;
import br.jus.stf.processamentoinicial.autuacao.domain.model.TipoPolo;
import br.jus.stf.processamentoinicial.distribuicao.application.ProcessoApplicationService;
import br.jus.stf.shared.ClasseId;
import br.jus.stf.shared.DocumentoId;
import br.jus.stf.shared.MinistroId;
import br.jus.stf.shared.PessoaId;

/**
 * @author Lucas Rodrigues
 */
public class PesquisaIntegrationTests extends AbstractIntegrationTests {

	@Autowired
	private PeticaoFactory peticaoFactory;

	@Autowired
	private PeticaoApplicationService peticaoApplicationService;

	@Autowired
	private ProcessoApplicationService processoApplicationService;

	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;

	@PersistenceContext
	private EntityManager entityManager;

	@Test
	public void pesquisar() throws Exception {
		PeticaoFisica peticao = peticaoApplicationService.registrar(1, 1, FormaRecebimento.SEDEX, "123");
		peticao.preautuar(new ClasseId("HC"));
		peticao.aceitar(new ClasseId("HC"));
		peticao.adicionarParte(new PartePeticao(new PessoaId(1L), TipoPolo.POLO_ATIVO));
		TipoPeca tipo = new TipoPeca(1L, "Petição Inicial");
		peticao.adicionarPeca(new PecaPeticao(new DocumentoId(1L), tipo, tipo.nome()));
		processoApplicationService.distribuir(peticao, new MinistroId(1L));
		elasticsearchTemplate.refresh("distribuicao", true);

		mockMvc.perform(post("/api/pesquisas").contentType(MediaType.APPLICATION_JSON).content("{\"indices\": [\"distribuicao\"], \"filtros\": {\"classe.sigla\": \"HC\"}, \"campos\": [\"classe.sigla\"] }"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].tipo", is("Processo")));
	}

}
