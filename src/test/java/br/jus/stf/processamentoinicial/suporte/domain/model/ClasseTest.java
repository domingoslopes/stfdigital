package br.jus.stf.processamentoinicial.suporte.domain.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.jus.stf.shared.ClasseId;

public class ClasseTest {
	
	private ClasseId idValido = new ClasseId("HC");
	private String nomeValido = "Habeas Corpus";
	private ClasseId idNulo = null;
	private String nomeVazio = "";
	private String nomeNulo = null;

	@Test
	public void criaClasseValida() {
		Classe classe = new Classe(idValido, nomeValido);
		
		assertNotNull(classe);
		assertEquals(classe.id(), idValido);
		assertEquals(classe.nome(), nomeValido);
	}
	
	@Test(expected=NullPointerException.class)
	public void criaClasseComIdNulo() {
		new Classe(idNulo, nomeValido);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void criaClasseComNomeVazio() {
		new Classe(idValido, nomeVazio);
	}
	
	@Test(expected=NullPointerException.class)
	public void criaClasseComNomeNulo() {
		new Classe(idValido, nomeNulo);
	}
	
	@Test
	public void classesIguais() {
		Classe classe1 = new Classe(idValido, nomeValido);
		Classe classe2 = new Classe(idValido, nomeValido);
		
		assertTrue(classe1.sameIdentityAs(classe2));
		assertTrue(classe1.equals(classe2));
	}
	
	@Test
	public void classesDiferentes() {
		Classe classe1 = new Classe(idValido, nomeValido);
		Classe classe2 = new Classe(new ClasseId("HD"), "Habeas Data");
		
		assertFalse(classe1.sameIdentityAs(classe2));
		assertFalse(classe1.equals(classe2));
	}

}
