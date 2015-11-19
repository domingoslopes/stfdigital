package br.jus.stf.shared;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PeticaoIdUnitTests {
	
	@Test
	public void criaPeticaoIdValido() {
		PeticaoId id = new PeticaoId(1L);
		
		assertNotNull(id);
		assertTrue(id.toLong() == 1L);
	}
	
	@Test(expected=NullPointerException.class)
	public void criaPeticaoIdComSequencialNula() {
		new PeticaoId(null);
	}
	
	@Test
	public void comparaPeticaoIdIguais() {
		PeticaoId id1 = new PeticaoId(1L);
		PeticaoId id2 = new PeticaoId(1L);
		
		assertTrue(id1.equals(id2));
	}
	
	@Test
	public void comparaPeticaoIdComValoresIguais() {
		PeticaoId id1 = new PeticaoId(1L);
		PeticaoId id2 = new PeticaoId(1L);
		
		assertTrue(id1.sameValueAs(id2));
	}
	
	@Test
	public void comparaPeticaoIdComHashesIguais() {
		PeticaoId id1 = new PeticaoId(1L);
		PeticaoId id2 = new PeticaoId(1L);
		
		assertTrue(id1.hashCode() == id2.hashCode());
	}
	
	@Test
	public void comparaPeticaoIdDiferentes() {
		PeticaoId id1 = new PeticaoId(1L);
		PeticaoId id2 = new PeticaoId(2L);
		
		assertFalse(id1.equals(id2));
	}
	
	@Test
	public void comparaPeticaoIdComValoresDiferentes() {
		PeticaoId id1 = new PeticaoId(1L);
		PeticaoId id2 = new PeticaoId(2L);
		
		assertFalse(id1.sameValueAs(id2));
	}
	
	@Test
	public void comparaPeticaoIdComHashesDiferentes() {
		PeticaoId id1 = new PeticaoId(1L);
		PeticaoId id2 = new PeticaoId(2L);
		
		assertFalse(id1.hashCode() == id2.hashCode());
	}
	
	@Test
	public void testaToString() {
		PeticaoId id1 = new PeticaoId(1L);
		
		assertEquals(id1.toString(), "1");
	}

}
