package br.jus.stf.plataforma.acessos.domain.model;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PermissaoPapelUnitTests {
	
	private Papel papel;
	
	@Before
	public void setUp() {
		TipoInformacao peticao = new TipoInformacao(1L, "Petição");
		Segmento peticaoEletronica = new Segmento(1L, "Petição eletrônica", peticao);
		Permissao criarPeticaoEletronica = new Permissao(1L, TipoPermissao.CRIAR, peticaoEletronica);
		TipoInformacao pessoa = new TipoInformacao(2L, "Pessoa");
		Segmento cadastramentoPessoa = new Segmento(2L, "Cadastramento de pessoa", pessoa);
		Segmento inclusaoPeca = new Segmento(3L, "Inclusão de peça", peticao);
		Permissao criarPeca = new Permissao(3L, TipoPermissao.VISUALIZAR, inclusaoPeca);
		Segmento pesquisaPessoa = new Segmento(4L, "Pesquisa de pessoa", pessoa);
		Permissao pesquisarPessoa = new Permissao(5L, TipoPermissao.PESQUISAR, pesquisaPessoa);
		Permissao criarPessoa = new Permissao(2L, TipoPermissao.CRIAR, cadastramentoPessoa);
		Set<Permissao> permissoes = new HashSet<Permissao>();
		
		permissoes.add(criarPeticaoEletronica);
		permissoes.add(criarPessoa);
		permissoes.add(criarPeca);
		permissoes.add(pesquisarPessoa);
		
		papel = new Papel(1L, "Advogado");
		papel.atribuirPermissoes(permissoes);
	}
	
	@Test
	public void papelPossuiAcessoNoRecurso() {
		TipoInformacao peticao = new TipoInformacao(1L, "Petição");
		Segmento peticaoEletronica = new Segmento(1L, "Petição eletrônica", peticao);
		Permissao criarPeticaoEletronica = new Permissao(1L, TipoPermissao.CRIAR, peticaoEletronica);
		TipoInformacao pessoa = new TipoInformacao(2L, "Pessoa");
		Segmento cadastramentoPessoa = new Segmento(2L, "Cadastramento de pessoa", pessoa);
		Permissao criarPessoa = new Permissao(2L, TipoPermissao.CRIAR, cadastramentoPessoa);		
		Segmento inclusaoPeca = new Segmento(3L, "Inclusão de peça", peticao);
		Permissao criarPeca = new Permissao(3L, TipoPermissao.VISUALIZAR, inclusaoPeca);
		Set<Permissao> permissoes = new HashSet<Permissao>();
		
		permissoes.add(criarPeticaoEletronica);
		permissoes.add(criarPessoa);
		permissoes.add(criarPeca);
		
		Recurso autuar = new Recurso("Autuar", TipoRecurso.ACAO, permissoes);
		
		Assert.assertTrue(papel.possuiAcessoNo(autuar));
	}
	
	@Test
	public void papelNaoPossuiAcessoNoRecurso() {
		TipoInformacao peticao = new TipoInformacao(1L, "Petição");
		Segmento peticaoEletronica = new Segmento(1L, "Petição Eletrônica", peticao);
		Permissao alterarPeticaoEletronica = new Permissao(4L, TipoPermissao.ALTERAR, peticaoEletronica);
		Permissao pesquisarPeticaoEletronica = new Permissao(5L, TipoPermissao.PESQUISAR, peticaoEletronica);
		Set<Permissao> permissoes = new HashSet<Permissao>();
		
		permissoes.add(alterarPeticaoEletronica);
		permissoes.add(pesquisarPeticaoEletronica);
		
		
		Recurso distribuir = new Recurso("Distribuir", TipoRecurso.ACAO, permissoes);
		
		Assert.assertFalse(papel.possuiAcessoNo(distribuir));
	}

}
