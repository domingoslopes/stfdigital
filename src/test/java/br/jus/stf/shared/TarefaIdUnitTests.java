package br.jus.stf.shared;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TarefaIdUnitTests {
	
	@Test
	public void criaTarefaIdValido() {
		TarefaId id = new TarefaId(1L);
		
		assertNotNull(id);
		assertTrue(id.toLong() == 1L);
	}
	
	@Test(expected=NullPointerException.class)
	public void criaTarefaIdComSequencialNula() {
		new TarefaId(null);
	}
	
	@Test
	public void comparaTarefaIdIguais() {
		TarefaId id1 = new TarefaId(1L);
		TarefaId id2 = new TarefaId(1L);
		
		assertTrue(id1.equals(id2));
	}
	
	@Test
	public void comparaTarefaIdComValoresIguais() {
		TarefaId id1 = new TarefaId(1L);
		TarefaId id2 = new TarefaId(1L);
		
		assertTrue(id1.sameValueAs(id2));
	}
	
	@Test
	public void comparaTarefaIdComHashesIguais() {
		TarefaId id1 = new TarefaId(1L);
		TarefaId id2 = new TarefaId(1L);
		
		assertTrue(id1.hashCode() == id2.hashCode());
	}
	
	@Test
	public void comparaTarefaIdDiferentes() {
		TarefaId id1 = new TarefaId(1L);
		TarefaId id2 = new TarefaId(2L);
		
		assertFalse(id1.equals(id2));
	}
	
	@Test
	public void comparaTarefaIdComValoresDiferentes() {
		TarefaId id1 = new TarefaId(1L);
		TarefaId id2 = new TarefaId(2L);
		
		assertFalse(id1.sameValueAs(id2));
	}
	
	@Test
	public void comparaTarefaIdComHashesDiferentes() {
		TarefaId id1 = new TarefaId(1L);
		TarefaId id2 = new TarefaId(2L);
		
		assertFalse(id1.hashCode() == id2.hashCode());
	}
	
	@Test
	public void testaToString() {
		TarefaId id1 = new TarefaId(1L);
		
		assertEquals(id1.toString(), "1");
	}

}
