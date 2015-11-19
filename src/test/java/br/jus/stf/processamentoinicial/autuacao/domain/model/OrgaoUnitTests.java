package br.jus.stf.processamentoinicial.autuacao.domain.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class OrgaoUnitTests {
	
	@Test
	public void criaOrgaoValida() {
		Orgao orgao = new Orgao(1L, "AGU");
		
		assertNotNull(orgao);
		assertEquals(orgao.toLong(), new Long(1L));
		assertEquals(orgao.nome(), "AGU");
	}
	
	@Test(expected=NullPointerException.class)
	public void criaOrgaoComIdNulo() {
		new Orgao(null, "AGU");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void criaOrgaoComNomeVazio() {
		new Orgao(1L, "");
	}
	
	@Test(expected=NullPointerException.class)
	public void criaOrgaoComNomeNulo() {
		new Orgao(1L, null);
	}
	
	@Test
	public void comparaOrgaosIguais() {
		Orgao orgao1 = new Orgao(1L, "AGU");
		Orgao orgao2 = new Orgao(1L, "AGU");
		
		assertTrue(orgao1.equals(orgao2));
	}
	
	@Test
	public void comparaOrgaosComIdentidadesIguais() {
		Orgao orgao1 = new Orgao(1L, "AGU");
		Orgao orgao2 = new Orgao(1L, "AGU");
		
		assertTrue(orgao1.sameValueAs(orgao2));
	}
	
	@Test
	public void comparaOrgaosComHashesIguais() {
		Orgao orgao1 = new Orgao(1L, "AGU");
		Orgao orgao2 = new Orgao(1L, "AGU");
		
		assertTrue(orgao1.hashCode() == orgao2.hashCode());
	}
	
	@Test
	public void comparaOrgaosDiferentes() {
		Orgao orgao1 = new Orgao(1L, "AGU");
		Orgao orgao2 = new Orgao(2L, "PGR");
		
		assertFalse(orgao1.equals(orgao2));
	}
	
	@Test
	public void comparaOrgaosComIdentidadesDiferentes() {
		Orgao orgao1 = new Orgao(1L, "AGU");
		Orgao orgao2 = new Orgao(2L, "PGR");
		
		assertFalse(orgao1.sameValueAs(orgao2));
	}
	
	@Test
	public void comparaOrgaosComHashesDiferentes() {
		Orgao orgao1 = new Orgao(1L, "AGU");
		Orgao orgao2 = new Orgao(2L, "PGR");
		
		assertFalse(orgao1.hashCode() == orgao2.hashCode());
	}

}
