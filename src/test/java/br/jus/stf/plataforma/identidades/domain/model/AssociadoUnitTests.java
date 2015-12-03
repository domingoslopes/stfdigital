package br.jus.stf.plataforma.identidades.domain.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.jus.stf.processamentoinicial.autuacao.domain.model.Orgao;
import br.jus.stf.shared.PessoaId;

public class AssociadoUnitTests {
	
	@Test
	public void criaAssociadoSemCargoValido() {
		Pessoa pessoa = new Pessoa(new PessoaId(1L), "José da Silva");
		Orgao orgao = new Orgao(1L, "AGU");
		Associado associado = new Associado(1L, pessoa, orgao, TipoAssociado.GESTOR);
		
		
		assertNotNull(associado);
		assertEquals(associado.pessoa(), pessoa);
		assertEquals(associado.orgao(), orgao);
		assertEquals(associado.tipo(), TipoAssociado.GESTOR);
		assertEquals(associado.cargoFuncao(), null);
	}
	
	@Test
	public void criaAssociadoComCargoValido() {
		Pessoa pessoa = new Pessoa(new PessoaId(1L), "José da Silva");
		Orgao orgao = new Orgao(1L, "AGU");
		Associado associado = new Associado(1L, pessoa, orgao, TipoAssociado.REPRESENTANTE, "Procurador");
		
		
		assertNotNull(associado);
		assertEquals(associado.pessoa(), pessoa);
		assertEquals(associado.orgao(), orgao);
		assertEquals(associado.tipo(), TipoAssociado.REPRESENTANTE);
		assertEquals(associado.cargoFuncao(), "Procurador");
	}
	
	@Test(expected=NullPointerException.class)
	public void criaAssociadoComIdNulo() {
		new Associado(null, new Pessoa(new PessoaId(1L), "José da Silva"), new Orgao(1L, "AGU"), TipoAssociado.ASSOCIADO);
	}
	
	@Test(expected=NullPointerException.class)
	public void criaAssociadoComPessoaNula() {
		new Associado(1L, null, new Orgao(1L, "AGU"), TipoAssociado.ASSOCIADO);
	}
	
	@Test(expected=NullPointerException.class)
	public void criaAssociadoComOrgaoNulo() {
		new Associado(1L, new Pessoa(new PessoaId(1L), "José da Silva"), null, TipoAssociado.ASSOCIADO);
	}
	
	@Test(expected=NullPointerException.class)
	public void criaAssociadoComTipoNulo() {
		new Associado(1L, new Pessoa(new PessoaId(1L), "José da Silva"), new Orgao(1L, "AGU"), null);
	}
	
	@Test(expected=NullPointerException.class)
	public void criaAssociadoComCargoNulo() {
		new Associado(1L, new Pessoa(new PessoaId(1L), "José da Silva"), new Orgao(1L, "AGU"), TipoAssociado.ASSOCIADO, null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void criaAssociadoComCargoBranco() {
		new Associado(1L, new Pessoa(new PessoaId(1L), "José da Silva"), new Orgao(1L, "AGU"), TipoAssociado.ASSOCIADO, "");
	}
	
	@Test
	public void comparaAssociadosIguais() {
		Associado associado1 = new Associado(1L, new Pessoa(new PessoaId(1L), "José da Silva"), new Orgao(1L, "AGU"), TipoAssociado.ASSOCIADO);
		Associado associado2 = new Associado(1L, new Pessoa(new PessoaId(1L), "José da Silva"), new Orgao(1L, "AGU"), TipoAssociado.ASSOCIADO);
		
		assertTrue(associado1.equals(associado2));
	}
	
	@Test
	public void comparaAssociadosComIdentidadesIguais() {
		Associado associado1 = new Associado(1L, new Pessoa(new PessoaId(1L), "José da Silva"), new Orgao(1L, "AGU"), TipoAssociado.ASSOCIADO);
		Associado associado2 = new Associado(1L, new Pessoa(new PessoaId(1L), "José da Silva"), new Orgao(1L, "AGU"), TipoAssociado.ASSOCIADO);
		
		assertTrue(associado1.sameIdentityAs(associado2));
	}
	
	@Test
	public void comparaAssociadosComHashesIguais() {
		Associado associado1 = new Associado(1L, new Pessoa(new PessoaId(1L), "José da Silva"), new Orgao(1L, "AGU"), TipoAssociado.ASSOCIADO);
		Associado associado2 = new Associado(1L, new Pessoa(new PessoaId(1L), "José da Silva"), new Orgao(1L, "AGU"), TipoAssociado.ASSOCIADO);
		
		assertTrue(associado1.hashCode() == associado2.hashCode());
	}
	
	@Test
	public void comparaAssociadosDiferentes() {
		Associado associado1 = new Associado(1L, new Pessoa(new PessoaId(1L), "José da Silva"), new Orgao(1L, "AGU"), TipoAssociado.ASSOCIADO);
		Associado associado2 = new Associado(2L, new Pessoa(new PessoaId(2L), "Maria Paiva"), new Orgao(1L, "AGU"), TipoAssociado.ASSOCIADO);
		
		assertFalse(associado1.equals(associado2));
	}
	
	@Test
	public void comparaAssociadosComIdentidadesDiferentes() {
		Associado associado1 = new Associado(1L, new Pessoa(new PessoaId(1L), "José da Silva"), new Orgao(1L, "AGU"), TipoAssociado.ASSOCIADO);
		Associado associado2 = new Associado(2L, new Pessoa(new PessoaId(2L), "Maria Paiva"), new Orgao(1L, "AGU"), TipoAssociado.ASSOCIADO);
		
		assertFalse(associado1.sameIdentityAs(associado2));
	}
	
	@Test
	public void comparaAssociadosComHashesDiferentes() {
		Associado associado1 = new Associado(1L, new Pessoa(new PessoaId(1L), "José da Silva"), new Orgao(1L, "AGU"), TipoAssociado.ASSOCIADO);
		Associado associado2 = new Associado(2L, new Pessoa(new PessoaId(2L), "Maria Paiva"), new Orgao(1L, "AGU"), TipoAssociado.ASSOCIADO);
		
		assertFalse(associado1.hashCode() == associado2.hashCode());
	}

}
