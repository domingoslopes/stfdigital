package br.jus.stf.processamentoinicial.autuacao.domain.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import br.jus.stf.shared.ClasseId;
import br.jus.stf.shared.PeticaoId;

public class PeticaoFisicaUnitTests {

	@Test
	public void criaPeticaoFisicaBalcaoValida() {
		PeticaoFisica peticaoFisica = new PeticaoFisica(new PeticaoId(1L), 5L, 4, 3, FormaRecebimento.BALCAO, null);

	    assertNotNull(peticaoFisica);
	    assertEquals(peticaoFisica.id(), new PeticaoId(1L));
	    assertEquals(peticaoFisica.numero(), new Long(5));
	    assertEquals(peticaoFisica.volumes(), new Integer(4));
		assertEquals(peticaoFisica.apensos(), new Integer(3));
		assertEquals(peticaoFisica.formaRecebimento(), FormaRecebimento.BALCAO);
		assertNull(peticaoFisica.numeroSedex());
		assertFalse(peticaoFisica.isEletronica());
	}
	
	@Test
	public void criaPeticaoFisicaSedexValida() {
		PeticaoFisica peticaoFisica = new PeticaoFisica(new PeticaoId(1L), 5L, 4, 3, FormaRecebimento.SEDEX, "AA100833276BR");

		assertEquals(peticaoFisica.formaRecebimento(), FormaRecebimento.SEDEX);
		assertEquals(peticaoFisica.numeroSedex(), "AA100833276BR");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void tentaCriarPeticaoFisicaMaloteInvalida() {
		new PeticaoFisica(new PeticaoId(1L), 5L, 4, 3, FormaRecebimento.MALOTE, "AA100833276BR");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void tentaCriarPeticaoFisicaSedexInvalida() {
		new PeticaoFisica(new PeticaoId(1L), 5L, 4, 3, FormaRecebimento.SEDEX, null);
	}
	
	@Test
	public void preautuaPeticaoFisica() {
		PeticaoFisica peticaoFisica = new PeticaoFisica(new PeticaoId(1L), 5L, 4, 3, FormaRecebimento.SEDEX, "AA100833276BR");
		
		peticaoFisica.preautuar(new ClasseId("ADI"));
		
		assertEquals(peticaoFisica.classeSugerida(), new ClasseId("ADI"));
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaPreautuarPeticaoFisicaSemSugerirClasse() {
		PeticaoFisica peticaoFisica = new PeticaoFisica(new PeticaoId(1L), 5L, 4, 3, FormaRecebimento.SEDEX, "AA100833276BR");
		
		peticaoFisica.preautuar(null);
	}
	
}
