package br.jus.stf.plataforma.identidades.domain.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.jus.stf.shared.PessoaId;

public class PessoaUnitTests {
	
	@Test
	public void criaPessoaValida() {
		Pessoa pessoa = new Pessoa(new PessoaId(1L), "José da Silva");
		
		assertNotNull(pessoa);
		assertEquals(pessoa.id(), new PessoaId(1L));
		assertEquals(pessoa.nome(), "José da Silva");
	}
	
	@Test(expected=NullPointerException.class)
	public void criaPessoaComIdNulo() {
		new Pessoa(null, "José da Silva");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void criaPessoaComNomeVazio() {
		new Pessoa(new PessoaId(1L), "");
	}
	
	@Test(expected=NullPointerException.class)
	public void criaPessoaComNomeNulo() {
		new Pessoa(new PessoaId(1L), null);
	}
	
	@Test
	public void comparaPessoasIguais() {
		Pessoa pessoa1 = new Pessoa(new PessoaId(1L), "José da Silva");
		Pessoa pessoa2 = new Pessoa(new PessoaId(1L), "José da Silva");
		
		assertTrue(pessoa1.equals(pessoa2));
	}
	
	@Test
	public void comparaPessoasComIdentidadesIguais() {
		Pessoa pessoa1 = new Pessoa(new PessoaId(1L), "José da Silva");
		Pessoa pessoa2 = new Pessoa(new PessoaId(1L), "José da Silva");
		
		assertTrue(pessoa1.sameIdentityAs(pessoa2));
	}
	
	@Test
	public void comparaPessoasComHashesIguais() {
		Pessoa pessoa1 = new Pessoa(new PessoaId(1L), "José da Silva");
		Pessoa pessoa2 = new Pessoa(new PessoaId(1L), "José da Silva");
		
		assertTrue(pessoa1.hashCode() == pessoa2.hashCode());
	}
	
	@Test
	public void comparaPessoasDiferentes() {
		Pessoa pessoa1 = new Pessoa(new PessoaId(1L), "José da Silva");
		Pessoa pessoa2 = new Pessoa(new PessoaId(2L), "Maria Antônia");
		
		assertFalse(pessoa1.equals(pessoa2));
	}
	
	@Test
	public void comparaPessoasComIdentidadesDiferentes() {
		Pessoa pessoa1 = new Pessoa(new PessoaId(1L), "José da Silva");
		Pessoa pessoa2 = new Pessoa(new PessoaId(2L), "Maria Antônia");
		
		assertFalse(pessoa1.sameIdentityAs(pessoa2));
	}
	
	@Test
	public void comparaPessoasComHashesDiferentes() {
		Pessoa pessoa1 = new Pessoa(new PessoaId(1L), "José da Silva");
		Pessoa pessoa2 = new Pessoa(new PessoaId(2L), "Maria Antônia");
		
		assertFalse(pessoa1.hashCode() == pessoa2.hashCode());
	}

}
