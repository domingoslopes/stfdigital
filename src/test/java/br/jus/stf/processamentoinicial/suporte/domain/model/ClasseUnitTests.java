package br.jus.stf.processamentoinicial.suporte.domain.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.jus.stf.shared.ClasseId;

public class ClasseUnitTests {
	
	@Test
	public void criaClasseValida() {
		Classe classe = new Classe(new ClasseId("HC"), "Habeas Corpus");
		
		assertNotNull(classe);
		assertEquals(classe.id(), new ClasseId("HC"));
		assertEquals(classe.nome(), "Habeas Corpus");
	}
	
	@Test(expected=NullPointerException.class)
	public void criaClasseComIdNulo() {
		new Classe(null, "Habeas Corpus");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void criaClasseComNomeVazio() {
		new Classe(new ClasseId("HC"), "");
	}
	
	@Test(expected=NullPointerException.class)
	public void criaClasseComNomeNulo() {
		new Classe(new ClasseId("HC"), null);
	}
	
	@Test
	public void comparaClassesIguais() {
		Classe classe1 = new Classe(new ClasseId("HC"), "Habeas Corpus");
		Classe classe2 = new Classe(new ClasseId("HC"), "Habeas Corpus");
		
		assertTrue(classe1.equals(classe2));
	}
	
	@Test
	public void comparaClassesComIdentidadesIguais() {
		Classe classe1 = new Classe(new ClasseId("HC"), "Habeas Corpus");
		Classe classe2 = new Classe(new ClasseId("HC"), "Habeas Corpus");
		
		assertTrue(classe1.sameIdentityAs(classe2));
	}
	
	@Test
	public void comparaClassesComHashesIguais() {
		Classe classe1 = new Classe(new ClasseId("HC"), "Habeas Corpus");
		Classe classe2 = new Classe(new ClasseId("HC"), "Habeas Corpus");
		
		assertTrue(classe1.hashCode() == classe2.hashCode());
	}
	
	@Test
	public void comparaClassesDiferentes() {
		Classe classe1 = new Classe(new ClasseId("HC"), "Habeas Corpus");
		Classe classe2 = new Classe(new ClasseId("HD"), "Habeas Data");
		
		assertFalse(classe1.equals(classe2));
	}
	
	@Test
	public void comparaClassesComIdentidadesDiferentes() {
		Classe classe1 = new Classe(new ClasseId("HC"), "Habeas Corpus");
		Classe classe2 = new Classe(new ClasseId("HD"), "Habeas Data");
		
		assertFalse(classe1.sameIdentityAs(classe2));
	}
	
	@Test
	public void comparaClassesComHashesDiferentes() {
		Classe classe1 = new Classe(new ClasseId("HC"), "Habeas Corpus");
		Classe classe2 = new Classe(new ClasseId("HD"), "Habeas Data");
		
		assertFalse(classe1.hashCode() == classe2.hashCode());
	}

}
