package br.jus.stf.plataforma.acessos.domain.model;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PermissaoUsuarioUnitTests {
	
	private Usuario usuario;
	
	@Before
	public void setUp() {
		TipoInformacao peticao = new TipoInformacao(1L, "Petição");
		TipoSegmento peticaoEletronica = new TipoSegmento(1L, "Petição Eletrônica");
		Segmento peticionamentoEletronico = new Segmento(1L, "Peticionamento eletrônico", peticao, peticaoEletronica);
		Permissao criarPeticaoEletronica = new Permissao(1L, peticionamentoEletronico, TipoPermissao.CRIAR);
		TipoInformacao pessoa = new TipoInformacao(2L, "Pessoa");
		Segmento cadastramentoPessoa = new Segmento(2L, "Cadastramento de pessoa", pessoa);
		Segmento inclusaoPeca = new Segmento(3L, "Inclusão de peça", peticao, peticaoEletronica);
		Permissao criarPeca = new Permissao(3L , inclusaoPeca, TipoPermissao.VISUALIZAR);
		Segmento pesquisaPessoa = new Segmento(4L, "Pesquisa de pessoa", pessoa);
		Permissao pesquisarPessoa = new Permissao(5L, pesquisaPessoa, TipoPermissao.PESQUISAR);
		Permissao criarPessoa = new Permissao(2L, cadastramentoPessoa, TipoPermissao.CRIAR);
		Set<Permissao> permissoes = new HashSet<Permissao>();
		
		permissoes.add(criarPeticaoEletronica);
		permissoes.add(criarPessoa);
		permissoes.add(criarPeca);
		permissoes.add(pesquisarPessoa);
		
		usuario = new Usuario(1L, "Maria Antonia", "mantonia", "12345678901", "mantonia@stf.jus.br", "(61) 9855-1247");
		usuario.atribuirPermissoes(permissoes);
	}
	
	@Test
	public void usuarioPossuiAcessoNoRecurso() {
		TipoInformacao peticao = new TipoInformacao(1L, "Petição");
		TipoSegmento peticaoEletronica = new TipoSegmento(1L, "Petição Eletrônica");
		Segmento peticionamentoEletronico = new Segmento(1L, "Peticionamento eletrônico", peticao, peticaoEletronica);
		Permissao criarPeticaoEletronica = new Permissao(1L, peticionamentoEletronico, TipoPermissao.CRIAR);
		TipoInformacao pessoa = new TipoInformacao(2L, "Pessoa");
		Segmento cadastramentoPessoa = new Segmento(2L, "Cadastramento de pessoa", pessoa);
		Permissao criarPessoa = new Permissao(2L, cadastramentoPessoa, TipoPermissao.CRIAR);		
		Segmento inclusaoPeca = new Segmento(3L, "Inclusão de peça", peticao, peticaoEletronica);
		Permissao criarPeca = new Permissao(3L, inclusaoPeca, TipoPermissao.VISUALIZAR);
		Set<Permissao> permissoes = new HashSet<Permissao>();
		
		permissoes.add(criarPeticaoEletronica);
		permissoes.add(criarPessoa);
		permissoes.add(criarPeca);
		
		Recurso autuar = new Recurso("Autuar", TipoRecurso.ACAO, permissoes);
		
		Assert.assertTrue(usuario.possuiAcessoNo(autuar));
	}
	
	@Test
	public void usuarioNaoPossuiAcessoNoRecurso() {
		TipoInformacao peticao = new TipoInformacao(1L, "Petição");
		TipoSegmento peticaoEletronica = new TipoSegmento(1L, "Petição Eletrônica");
		Segmento peticionamentoEletronico = new Segmento(1L, "Peticionamento eletrônico", peticao, peticaoEletronica);
		Permissao alterarPeticaoEletronica = new Permissao(4L, peticionamentoEletronico, TipoPermissao.ALTERAR);
		Permissao pesquisarPeticaoEletronica = new Permissao(5L, peticionamentoEletronico, TipoPermissao.PESQUISAR);
		Set<Permissao> permissoes = new HashSet<Permissao>();
		
		permissoes.add(alterarPeticaoEletronica);
		permissoes.add(pesquisarPeticaoEletronica);
		
		
		Recurso distribuir = new Recurso("Distribuir", TipoRecurso.ACAO, permissoes);
		
		Assert.assertFalse(usuario.possuiAcessoNo(distribuir));
	}
	
	@Test
	public void usuarioPossuiAcessoNoRecursoPeloPapel() {
		TipoInformacao peticao = new TipoInformacao(1L, "Petição");
		TipoSegmento peticaoEletronica = new TipoSegmento(1L, "Petição Eletrônica");
		Segmento peticionamentoEletronico = new Segmento(1L, "Peticionamento eletrônico", peticao, peticaoEletronica);
		Permissao alterarPeticaoEletronica = new Permissao(4L, peticionamentoEletronico, TipoPermissao.ALTERAR);
		Permissao pesquisarPeticaoEletronica = new Permissao(5L, peticionamentoEletronico, TipoPermissao.PESQUISAR);
		Set<Permissao> permissoes = new HashSet<Permissao>();
		
		permissoes.add(alterarPeticaoEletronica);
		permissoes.add(pesquisarPeticaoEletronica);
		
		Papel distribuidor = new Papel(1L, "Distribuidor");
		Set<Papel> papeis = new HashSet<Papel>();
		
		papeis.add(distribuidor);
		distribuidor.atribuirPermissoes(permissoes);
		usuario.atribuirPapeis(papeis);
		
		Recurso distribuir = new Recurso("Distribuir", TipoRecurso.ACAO, permissoes);
		
		Assert.assertTrue(usuario.possuiAcessoNo(distribuir));
	}
	
	@Test
	public void usuarioPossuiAcessoNoRecursoPeloGrupo() {
		TipoInformacao peticao = new TipoInformacao(1L, "Petição");
		TipoSegmento peticaoEletronica = new TipoSegmento(1L, "Petição Eletrônica");
		Segmento peticionamentoEletronico = new Segmento(1L, "Peticionamento eletrônico", peticao, peticaoEletronica);
		Permissao alterarPeticaoEletronica = new Permissao(4L, peticionamentoEletronico, TipoPermissao.ALTERAR);
		Permissao pesquisarPeticaoEletronica = new Permissao(5L, peticionamentoEletronico, TipoPermissao.PESQUISAR);
		Set<Permissao> permissoes = new HashSet<Permissao>();
		
		permissoes.add(alterarPeticaoEletronica);
		permissoes.add(pesquisarPeticaoEletronica);
		
		Grupo agu = new Grupo(1L, "AGU", TipoGrupo.ORGAO);
		Set<Grupo> grupos = new HashSet<Grupo>();
		
		grupos.add(agu);
		agu.atribuirPermissoes(permissoes);
		usuario.atribuirGrupos(grupos);
		
		Recurso distribuir = new Recurso("Distribuir", TipoRecurso.ACAO, permissoes);
		
		Assert.assertTrue(usuario.possuiAcessoNo(distribuir));
	}

}
