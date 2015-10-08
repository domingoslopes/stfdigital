package br.jus.stf.processamentoinicial.suporte.domain.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.jus.stf.shared.MinistroId;

public class MinistroTest {
	
	private MinistroId idValido = new MinistroId(1L);
	private String nomeValido = "Carlos Ayres Britto";
	private MinistroId idNulo = null;
	private String nomeVazio = "";
	private String nomeNulo = null;

	@Test
	public void criaMinistroValido() {
		Ministro ministro = new Ministro(idValido, nomeValido);
		
		assertNotNull(ministro);
		assertTrue(ministro.id() == idValido);
		assertTrue(ministro.nome() == nomeValido);
	}
	
	@Test(expected=NullPointerException.class)
	public void criaMinistroComIdNulo() {
		new Ministro(idNulo, nomeValido);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void criaMinistroComNomeVazio() {
		new Ministro(idValido, nomeVazio);
	}
	
	@Test(expected=NullPointerException.class)
	public void criaMinistroComNomeNulo() {
		new Ministro(idValido, nomeNulo);
	}
	
	@Test
	public void ministrosIguais() {
		Ministro ministro1 = new Ministro(idValido, nomeValido);
		Ministro ministro2 = new Ministro(idValido, nomeValido);
		
		assertTrue(ministro1.sameIdentityAs(ministro2));
		assertTrue(ministro1.equals(ministro2));
	}
	
	@Test
	public void ministrosDiferentes() {
		Ministro ministro1 = new Ministro(idValido, nomeValido);
		Ministro ministro2 = new Ministro(new MinistroId(2L), "Ellen Gracie");
		
		assertFalse(ministro1.sameIdentityAs(ministro2));
		assertFalse(ministro1.equals(ministro2));
	}

}
