package br.jus.stf.plataforma.acessos.domain.model;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.jus.stf.plataforma.identidades.domain.model.Pessoa;
import br.jus.stf.plataforma.shared.security.resource.ResourceType;
import br.jus.stf.shared.GrupoId;
import br.jus.stf.shared.PapelId;
import br.jus.stf.shared.PessoaId;
import br.jus.stf.shared.RecursoId;
import br.jus.stf.shared.UsuarioId;

public class PermissaoUsuarioUnitTests {
	
	private Usuario usuario;
	private Recurso criarPeticaoEletronica;
	private Set<Recurso> recursos;
	
	@Before
	public void setUp() {
		criarPeticaoEletronica = new Recurso(new RecursoId(2L), "Criar petição eletrônica", ResourceType.ACAO);
		recursos = new HashSet<Recurso>(0);
		
		recursos.add(criarPeticaoEletronica);
		
		usuario = new Usuario(new UsuarioId(1L), new Pessoa(new PessoaId(1L), "Maria Antonia", "12345678901", "mantonia@stf.jus.br", "(61) 9855-1247"), "mantonia");
		usuario.atribuirRecursos(recursos);
	}
	
	@Test
	public void usuarioPossuiAcessoNoRecurso() {
		Assert.assertTrue(usuario.possuiAcessoNo(criarPeticaoEletronica));
	}
	
	@Test
	public void usuarioNaoPossuiAcessoNoRecurso() {
		Recurso distribuir = new Recurso(new RecursoId(1L), "Distribuir", ResourceType.ACAO);
		
		Assert.assertFalse(usuario.possuiAcessoNo(distribuir));
	}
	
	@Test
	public void usuarioPossuiAcessoNoRecursoPeloPapel() {
		Recurso distribuir = new Recurso(new RecursoId(1L), "Distribuir", ResourceType.ACAO);
		Set<Recurso> recursosPapel = new HashSet<Recurso>(0);
		Papel distribuidor = new Papel(new PapelId(1L), "Distribuidor");
		Set<Papel> papeis = new HashSet<Papel>();
		
		recursosPapel.add(distribuir);
		papeis.add(distribuidor);
		distribuidor.atribuirRecursos(recursosPapel);
		usuario.atribuirPapeis(papeis);
		
		Assert.assertTrue(usuario.possuiAcessoNo(distribuir));
	}
	
	@Test
	public void usuarioPossuiAcessoNoRecursoPeloGrupo() {
		Recurso distribuir = new Recurso(new RecursoId(1L), "Distribuir", ResourceType.ACAO);
		Set<Recurso> recursosGrupo = new HashSet<Recurso>(0);
		
		Grupo agu = new Grupo(new GrupoId(1L), "AGU", TipoGrupo.ORGAO_CONVENIADO);
		Set<Grupo> grupos = new HashSet<Grupo>();
		
		recursosGrupo.add(distribuir);
		grupos.add(agu);
		agu.atribuirRecursos(recursosGrupo);
		usuario.atribuirGrupos(grupos);
		
		Assert.assertTrue(usuario.possuiAcessoNo(distribuir));
	}

}
