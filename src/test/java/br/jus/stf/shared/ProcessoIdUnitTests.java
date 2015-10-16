package br.jus.stf.shared;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ProcessoIdUnitTests {
	
	@Test
	public void criaProcessoIdValido() {
		ProcessoId id = new ProcessoId(1L);
		
		assertNotNull(id);
		assertTrue(id.toLong() == 1L);
	}
	
	@Test(expected=NullPointerException.class)
	public void criaProcessoIdComSequencialNula() {
		new ProcessoId(null);
	}
	
	@Test
	public void comparaProcessoIdIguais() {
		ProcessoId id1 = new ProcessoId(1L);
		ProcessoId id2 = new ProcessoId(1L);
		
		assertTrue(id1.equals(id2));
	}
	
	@Test
	public void comparaProcessoIdComValoresIguais() {
		ProcessoId id1 = new ProcessoId(1L);
		ProcessoId id2 = new ProcessoId(1L);
		
		assertTrue(id1.sameValueAs(id2));
	}
	
	@Test
	public void comparaProcessoIdComHashesIguais() {
		ProcessoId id1 = new ProcessoId(1L);
		ProcessoId id2 = new ProcessoId(1L);
		
		assertTrue(id1.hashCode() == id2.hashCode());
	}
	
	@Test
	public void comparaProcessoIdDiferentes() {
		ProcessoId id1 = new ProcessoId(1L);
		ProcessoId id2 = new ProcessoId(2L);
		
		assertFalse(id1.equals(id2));
	}
	
	@Test
	public void comparaProcessoIdComValoresDiferentes() {
		ProcessoId id1 = new ProcessoId(1L);
		ProcessoId id2 = new ProcessoId(2L);
		
		assertFalse(id1.sameValueAs(id2));
	}
	
	@Test
	public void comparaProcessoIdComHashesDiferentes() {
		ProcessoId id1 = new ProcessoId(1L);
		ProcessoId id2 = new ProcessoId(2L);
		
		assertFalse(id1.hashCode() == id2.hashCode());
	}
	
	@Test
	public void testaToString() {
		ProcessoId id1 = new ProcessoId(1L);
		
		assertEquals(id1.toString(), "1");
	}

}
