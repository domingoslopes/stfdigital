package br.jus.stf.processamentoinicial.autuacao.domain.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import br.jus.stf.processamentoinicial.suporte.domain.model.TipoProcesso;
import br.jus.stf.shared.ClasseId;
import br.jus.stf.shared.DocumentoId;
import br.jus.stf.shared.PessoaId;
import br.jus.stf.shared.PeticaoId;

public class PeticaoEletronicaUnitTests {
	
	private Set<PartePeticao> partes;
	private Set<PecaPeticao> pecas;
	
	@Before
	public void setUp() {
		partes = new HashSet<PartePeticao>(0);
		partes.add(new PartePeticao(new PessoaId(1L), TipoPolo.POLO_ATIVO));
		partes.add(new PartePeticao(new PessoaId(2L), TipoPolo.POLO_PASSIVO));
		partes.add(new PartePeticao(new PessoaId(3L), TipoPolo.POLO_PASSIVO));
		
		pecas = new LinkedHashSet<PecaPeticao>(0);
		pecas.add(new PecaPeticao(new DocumentoId(1L), new TipoPeca(1L, "Petição inicial"), "Petição inicial"));
	}

	@Test
	public void criaPeticaoEletronicaSemRepresentacaoValida() {
		PeticaoEletronica peticaoEletronica = new PeticaoEletronica(new PeticaoId(1L), 5L, "PETICIONADOR", new ClasseId("HC"), partes, pecas, TipoProcesso.ORIGINARIO);

	    assertNotNull(peticaoEletronica);
	    assertEquals(peticaoEletronica.id(), new PeticaoId(1L));
	    assertEquals(peticaoEletronica.numero(), new Long(5));
	    assertEquals(peticaoEletronica.classeSugerida(), new ClasseId("HC"));
	    assertFalse(peticaoEletronica.hasRepresentacao());
	    assertNull(peticaoEletronica.orgaoRepresentado());
	    assertEquals(peticaoEletronica.partesPoloAtivo().size(), 1);
		assertEquals(peticaoEletronica.partesPoloPassivo().size(), 2);
		assertEquals(peticaoEletronica.pecas(), pecas);
		assertTrue(peticaoEletronica.isEletronica());
	}
	
	@Test
	public void criaPeticaoEletronicaComRepresentacaoValida() {
		PeticaoEletronica peticaoEletronica = new PeticaoEletronica(new PeticaoId(1L), 5L, "PETICIONADOR", new ClasseId("HC"), partes, pecas, new Orgao(1L, "PGR"), TipoProcesso.ORIGINARIO);

	    assertNotNull(peticaoEletronica);
	    assertEquals(peticaoEletronica.id(), new PeticaoId(1L));
	    assertEquals(peticaoEletronica.numero(), new Long(5));
	    assertEquals(peticaoEletronica.classeSugerida(), new ClasseId("HC"));
	    assertTrue(peticaoEletronica.hasRepresentacao());
	    assertEquals(peticaoEletronica.orgaoRepresentado(), new Orgao(1L, "PGR"));
	    assertEquals(peticaoEletronica.partesPoloAtivo().size(), 1);
		assertEquals(peticaoEletronica.partesPoloPassivo().size(), 2);
		assertEquals(peticaoEletronica.pecas(), pecas);
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaCriarPeticaoEletronicaComRepresentacaoSemOrgao() {
		new PeticaoEletronica(new PeticaoId(1L), 5L, "PETICIONADOR", new ClasseId("HC"), partes, pecas, null, TipoProcesso.ORIGINARIO);
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaCriarPeticaoEletronicaSemClasseSugerida() {
		new PeticaoEletronica(new PeticaoId(1L), 5L, "PETICIONADOR", null, partes, pecas, TipoProcesso.ORIGINARIO);
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaCriarPeticaoEletronicaComPartesNulo() {
		new PeticaoEletronica(new PeticaoId(1L), 5L, "PETICIONADOR", new ClasseId("HC"), null, pecas, TipoProcesso.ORIGINARIO);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void tentaCriarPeticaoEletronicaComPartesVazio() {
		partes.clear();
		
		new PeticaoEletronica(new PeticaoId(1L), 5L, "PETICIONADOR", new ClasseId("HC"), partes, pecas, TipoProcesso.ORIGINARIO);
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaCriarPeticaoEletronicaComPecasNulo() {
		new PeticaoEletronica(new PeticaoId(1L), 5L, "PETICIONADOR", new ClasseId("HC"), partes, null, TipoProcesso.ORIGINARIO);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void tentaCriarPeticaoEletronicaComPecasVazio() {
		pecas.clear();
		
		new PeticaoEletronica(new PeticaoId(1L), 5L, "PETICIONADOR", new ClasseId("HC"), partes, pecas, TipoProcesso.ORIGINARIO);
	}
}
