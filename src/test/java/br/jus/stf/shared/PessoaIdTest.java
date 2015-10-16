package br.jus.stf.shared;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PessoaIdTest {
	
	@Test
	public void criaPessoaIdValido() {
		PessoaId id = new PessoaId(1L);
		
		assertNotNull(id);
		assertTrue(id.toLong() == 1L);
	}
	
	@Test(expected=NullPointerException.class)
	public void criaPessoaIdComSequencialNula() {
		new PessoaId(null);
	}
	
	@Test
	public void comparaPessoaIdIguais() {
		PessoaId id1 = new PessoaId(1L);
		PessoaId id2 = new PessoaId(1L);
		
		assertTrue(id1.equals(id2));
	}
	
	@Test
	public void comparaPessoaIdComValoresIguais() {
		PessoaId id1 = new PessoaId(1L);
		PessoaId id2 = new PessoaId(1L);
		
		assertTrue(id1.sameValueAs(id2));
	}
	
	@Test
	public void comparaPessoaIdComHashesIguais() {
		PessoaId id1 = new PessoaId(1L);
		PessoaId id2 = new PessoaId(1L);
		
		assertTrue(id1.hashCode() == id2.hashCode());
	}
	
	@Test
	public void comparaPessoaIdDiferentes() {
		PessoaId id1 = new PessoaId(1L);
		PessoaId id2 = new PessoaId(2L);
		
		assertFalse(id1.equals(id2));
	}
	
	@Test
	public void comparaPessoaIdComValoresDiferentes() {
		PessoaId id1 = new PessoaId(1L);
		PessoaId id2 = new PessoaId(2L);
		
		assertFalse(id1.sameValueAs(id2));
	}
	
	@Test
	public void comparaPessoaIdComHashesDiferentes() {
		PessoaId id1 = new PessoaId(1L);
		PessoaId id2 = new PessoaId(2L);
		
		assertFalse(id1.hashCode() == id2.hashCode());
	}
	
	@Test
	public void testaToString() {
		PessoaId id1 = new PessoaId(1L);
		
		assertEquals(id1.toString(), "1");
	}

}
