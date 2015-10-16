package br.jus.stf.shared;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ClasseIdTest {
	
	@Test
	public void criaClasseIdValida() {
		ClasseId id = new ClasseId("HC");
		
		assertNotNull(id);
		assertEquals(id.toString(), "HC");
	}
	
	@Test(expected=NullPointerException.class)
	public void criaClasseIdComSiglaNula() {
		new ClasseId(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void criaClasseIdComSiglaVazia() {
		new ClasseId("");
	}
	
	@Test
	public void comparaClasseIdIguais() {
		ClasseId id1 = new ClasseId("HC");
		ClasseId id2 = new ClasseId("HC");
		
		assertTrue(id1.equals(id2));
	}
	
	@Test
	public void comparaClasseIdComValoresIguais() {
		ClasseId id1 = new ClasseId("HC");
		ClasseId id2 = new ClasseId("HC");
		
		assertTrue(id1.sameValueAs(id2));
	}
	
	@Test
	public void comparaClasseIdComHashesIguais() {
		ClasseId id1 = new ClasseId("HC");
		ClasseId id2 = new ClasseId("HC");
		
		assertTrue(id1.hashCode() == id2.hashCode());
	}
	
	@Test
	public void comparaClasseIdDiferentes() {
		ClasseId id1 = new ClasseId("HC");
		ClasseId id2 = new ClasseId("HD");
		
		assertFalse(id1.equals(id2));
	}
	
	@Test
	public void comparaClasseIdComValoresDiferentes() {
		ClasseId id1 = new ClasseId("HC");
		ClasseId id2 = new ClasseId("HD");
		
		assertFalse(id1.sameValueAs(id2));
	}
	
	@Test
	public void comparaClasseIdComHashesDiferentes() {
		ClasseId id1 = new ClasseId("HC");
		ClasseId id2 = new ClasseId("HD");
		
		assertFalse(id1.hashCode() == id2.hashCode());
	}

}
