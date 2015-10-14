package br.jus.stf.processamentoinicial.autuacao.application;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.jus.stf.plataforma.shared.tests.AbstractIntegrationTests;
import br.jus.stf.processamentoinicial.autuacao.domain.model.FormaRecebimento;
import br.jus.stf.processamentoinicial.autuacao.domain.model.Peticao;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoFactory;

/**
 * @author Lucas Rodrigues
 */
public class PeticaoApplicationEventIntegrationTests extends AbstractIntegrationTests {

	@Autowired
	private PeticaoApplicationEvent peticaoApplicationEvent;
	
	@Autowired
	private PeticaoFactory peticaoFactory;
	
	@Test
	public void peticaoRecebidaEvent() {
		Peticao peticao = peticaoFactory.criarPeticaoFisica(1, 1, FormaRecebimento.SEDEX, "123");
		peticaoApplicationEvent.peticaoRecebida(peticao);
	}
	
}
