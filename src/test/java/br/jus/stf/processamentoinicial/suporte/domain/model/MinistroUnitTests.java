package br.jus.stf.processamentoinicial.suporte.domain.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.jus.stf.shared.MinistroId;

public class MinistroUnitTests {
	
	@Test
	public void criaMinistroValido() {
		Ministro ministro = new Ministro(new MinistroId(1L), "Carlos Ayres Britto");
		
		assertNotNull(ministro);
		assertEquals(ministro.id(), new MinistroId(1L));
		assertEquals(ministro.nome(), "Carlos Ayres Britto");
	}
	
	@Test(expected=NullPointerException.class)
	public void criaMinistroComIdNulo() {
		new Ministro(null, "Carlos Ayres Britto");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void criaMinistroComNomeVazio() {
		new Ministro(new MinistroId(1L), "");
	}
	
	@Test(expected=NullPointerException.class)
	public void criaMinistroComNomeNulo() {
		new Ministro(new MinistroId(1L), null);
	}
	
	@Test
	public void comparaMinistrosIguais() {
		Ministro ministro1 = new Ministro(new MinistroId(1L), "Carlos Ayres Britto");
		Ministro ministro2 = new Ministro(new MinistroId(1L), "Carlos Ayres Britto");
		
		assertTrue(ministro1.equals(ministro2));
	}
	
	@Test
	public void comparaMinistrosComIdentidadesIguais() {
		Ministro ministro1 = new Ministro(new MinistroId(1L), "Carlos Ayres Britto");
		Ministro ministro2 = new Ministro(new MinistroId(1L), "Carlos Ayres Britto");
		
		assertTrue(ministro1.sameIdentityAs(ministro2));
	}
	
	@Test
	public void comparaMinistrosComHashesIguais() {
		Ministro ministro1 = new Ministro(new MinistroId(1L), "Carlos Ayres Britto");
		Ministro ministro2 = new Ministro(new MinistroId(1L), "Carlos Ayres Britto");
		
		assertTrue(ministro1.hashCode() == ministro2.hashCode());
	}
	
	@Test
	public void comparaMinistrosDiferentes() {
		Ministro ministro1 = new Ministro(new MinistroId(1L), "Carlos Ayres Britto");
		Ministro ministro2 = new Ministro(new MinistroId(2L), "Ellen Gracie");
		
		assertFalse(ministro1.equals(ministro2));
	}
	
	@Test
	public void comparaMinistrosComIdentidadesDiferentes() {
		Ministro ministro1 = new Ministro(new MinistroId(1L), "Carlos Ayres Britto");
		Ministro ministro2 = new Ministro(new MinistroId(2L), "Ellen Gracie");
		
		assertFalse(ministro1.sameIdentityAs(ministro2));
	}
	
	@Test
	public void comparaMinistrosComHashesDiferentes() {
		Ministro ministro1 = new Ministro(new MinistroId(1L), "Carlos Ayres Britto");
		Ministro ministro2 = new Ministro(new MinistroId(2L), "Ellen Gracie");
		
		assertFalse(ministro1.hashCode() == ministro2.hashCode());
	}

}
