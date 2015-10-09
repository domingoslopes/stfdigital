package br.jus.stf.pesquisa;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.http.MediaType;

import br.jus.stf.AbstractIntegrationTests;
import br.jus.stf.processamentoinicial.autuacao.application.PeticaoApplicationService;
import br.jus.stf.processamentoinicial.autuacao.domain.model.FormaRecebimento;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PartePeticao;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoFactory;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoFisica;
import br.jus.stf.processamentoinicial.autuacao.domain.model.TipoPolo;
import br.jus.stf.processamentoinicial.distribuicao.application.ProcessoApplicationService;
import br.jus.stf.shared.ClasseId;
import br.jus.stf.shared.DocumentoId;
import br.jus.stf.shared.MinistroId;
import br.jus.stf.shared.PessoaId;

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
	private ProcessoApplicationService processoApplicationService;
	
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	
	@Test
	public void pesquisar() throws Exception {
		
		PeticaoFisica peticao = peticaoApplicationService.registrar(1, 1, FormaRecebimento.SEDEX, "123");
		peticao.preautuar(new ClasseId("HC"));
		peticao.aceitar(new ClasseId("HC"));
		peticao.adicionarParte(new PartePeticao(new PessoaId(1L), TipoPolo.POLO_ATIVO));
		peticao.adicionarDocumento(new DocumentoId(1L));
		processoApplicationService.distribuir(peticao, new MinistroId(1L));
		elasticsearchTemplate.refresh("distribuicao", true);
		
		mockMvc.perform(post("/api/pesquisas/sugestoes")
    			.contentType(MediaType.APPLICATION_JSON)
    			//.content("{\"indices\": [\"distribuicao\"], \"filtros\": {\"identificacao\": \"HC\"}, \"campos\": [\"identificacao\", \"relator.codigo\"], \"ordenadores\": {\"identificacao\" : \"ASC\"} }"))
    			.content("{\"indices\": [\"distribuicao\"], \"filtros\": {\"identificacao_sugestao\": \"HC\"} }"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].tipo", is("Processo")))
			.andDo(print());

	}
	
}
