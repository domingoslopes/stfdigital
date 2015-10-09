package br.jus.stf.autuacao.application;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.jus.stf.AbstractIntegrationTests;
import br.jus.stf.processamentoinicial.autuacao.application.PeticaoApplicationEvent;
import br.jus.stf.processamentoinicial.autuacao.domain.model.FormaRecebimento;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PartePeticao;
import br.jus.stf.processamentoinicial.autuacao.domain.model.Peticao;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoFactory;
import br.jus.stf.processamentoinicial.autuacao.domain.model.TipoPolo;
import br.jus.stf.processamentoinicial.distribuicao.application.ProcessoApplicationEvent;
import br.jus.stf.processamentoinicial.distribuicao.domain.model.Processo;
import br.jus.stf.processamentoinicial.distribuicao.domain.model.ProcessoFactory;
import br.jus.stf.shared.ClasseId;
import br.jus.stf.shared.DocumentoId;
import br.jus.stf.shared.MinistroId;
import br.jus.stf.shared.PessoaId;

/**
 * @author Lucas.Rodrigues
 *
 */
public class ProcessamentoInicialEventIntegrationTests extends AbstractIntegrationTests {

	@Autowired
	private PeticaoApplicationEvent peticaoApplicationEvent;
	
	@Autowired
	private ProcessoApplicationEvent processoApplicationEvent;
	
	@Autowired
	private PeticaoFactory peticaoFactory;
	
	@Test
	public void peticaoRecebidaEvent() {
		Peticao peticao = peticaoFactory.criarPeticaoFisica(1, 1, FormaRecebimento.SEDEX, "123");
		peticaoApplicationEvent.peticaoRecebida(peticao);
	}
	
	@Test
	public void processoDistribuidoEvent() {
		Peticao peticao = peticaoFactory.criarPeticaoFisica(1, 1, FormaRecebimento.SEDEX, "123");
		peticao.adicionarDocumento(new DocumentoId(1L));
		peticao.adicionarParte(new PartePeticao(new PessoaId(1L), TipoPolo.POLO_ATIVO));
		Processo processo = ProcessoFactory.criarProcesso(new ClasseId("HC"), new MinistroId(1L), peticao);
		processoApplicationEvent.processoDistribuido(processo);
	}
	
}
