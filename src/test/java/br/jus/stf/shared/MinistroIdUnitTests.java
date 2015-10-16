package br.jus.stf.shared;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MinistroIdUnitTests {
	
	@Test
	public void criaMinistroIdValido() {
		MinistroId id = new MinistroId(1L);
		
		assertNotNull(id);
		assertTrue(id.toLong() == 1L);
	}
	
	@Test(expected=NullPointerException.class)
	public void criaMinistroIdComCodigoNulo() {
		new MinistroId(null);
	}
	
	@Test
	public void comparaMinistroIdIguais() {
		MinistroId id1 = new MinistroId(1L);
		MinistroId id2 = new MinistroId(1L);
		
		assertTrue(id1.equals(id2));
	}
	
	@Test
	public void comparaMinistroIdComValoresIguais() {
		MinistroId id1 = new MinistroId(1L);
		MinistroId id2 = new MinistroId(1L);
		
		assertTrue(id1.sameValueAs(id2));
	}
	
	@Test
	public void comparaMinistroIdComHashesIguais() {
		MinistroId id1 = new MinistroId(1L);
		MinistroId id2 = new MinistroId(1L);
		
		assertTrue(id1.hashCode() == id2.hashCode());
	}
	
	@Test
	public void comparaMinistroIdDiferentes() {
		MinistroId id1 = new MinistroId(1L);
		MinistroId id2 = new MinistroId(2L);
		
		assertFalse(id1.equals(id2));
	}
	
	@Test
	public void comparaMinistroIdComValoresDiferentes() {
		MinistroId id1 = new MinistroId(1L);
		MinistroId id2 = new MinistroId(2L);
		
		assertFalse(id1.sameValueAs(id2));
	}
	
	@Test
	public void comparaMinistroIdComHashesDiferentes() {
		MinistroId id1 = new MinistroId(1L);
		MinistroId id2 = new MinistroId(2L);
		
		assertFalse(id1.hashCode() == id2.hashCode());
	}
	
	@Test
	public void testaToString() {
		MinistroId id1 = new MinistroId(1L);
		
		assertEquals(id1.toString(), "1");
	}

}
