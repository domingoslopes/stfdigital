package br.jus.stf.plataforma.identidades.domain.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.jus.stf.shared.PessoaId;

public class PessoaTest {
	
	private PessoaId idValido = new PessoaId(1L);
	private String nomeValido = "José da Silva";
	private PessoaId idNulo = null;
	private String nomeVazio = "";
	private String nomeNulo = null;

	@Test
	public void criaPessoaValida() {
		Pessoa pessoa = new Pessoa(idValido, nomeValido);
		
		assertNotNull(pessoa);
		assertEquals(pessoa.id(), idValido);
		assertEquals(pessoa.nome(), nomeValido);
	}
	
	@Test(expected=NullPointerException.class)
	public void criaPessoaComIdNulo() {
		new Pessoa(idNulo, nomeValido);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void criaPessoaComNomeVazio() {
		new Pessoa(idValido, nomeVazio);
	}
	
	@Test(expected=NullPointerException.class)
	public void criaPessoaComNomeNulo() {
		new Pessoa(idValido, nomeNulo);
	}
	
	@Test
	public void pessoasIguais() {
		Pessoa pessoa1 = new Pessoa(idValido, nomeValido);
		Pessoa pessoa2 = new Pessoa(idValido, nomeValido);
		
		assertTrue(pessoa1.sameIdentityAs(pessoa2));
		assertTrue(pessoa1.equals(pessoa2));
	}
	
	@Test
	public void pessoasDiferentes() {
		Pessoa pessoa1 = new Pessoa(idValido, nomeValido);
		Pessoa pessoa2 = new Pessoa(new PessoaId(2L), "Maria Antônia");
		
		assertFalse(pessoa1.sameIdentityAs(pessoa2));
		assertFalse(pessoa1.equals(pessoa2));
	}

}
