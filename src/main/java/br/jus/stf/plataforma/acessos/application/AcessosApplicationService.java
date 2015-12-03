package br.jus.stf.plataforma.acessos.application;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.jus.stf.plataforma.acessos.domain.model.Grupo;
import br.jus.stf.plataforma.acessos.domain.model.GrupoRepository;
import br.jus.stf.plataforma.acessos.domain.model.Papel;
import br.jus.stf.plataforma.acessos.domain.model.PapelRepository;
import br.jus.stf.plataforma.acessos.domain.model.Permissao;
import br.jus.stf.plataforma.acessos.domain.model.RecursoRepository;
import br.jus.stf.plataforma.acessos.domain.model.TipoRecurso;
import br.jus.stf.plataforma.acessos.domain.model.Usuario;
import br.jus.stf.plataforma.acessos.domain.model.UsuarioRepository;
import br.jus.stf.shared.GrupoId;
import br.jus.stf.shared.PapelId;
import br.jus.stf.shared.UsuarioId;

/**
 * @author Lucas.Rodrigues
 *
 */
@Service
@Transactional
public class AcessosApplicationService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private RecursoRepository recursoRepository;
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private PapelRepository papelRepository;
	
	public Set<Permissao> carregarPermissoesUsuario(String login) {
		Usuario usuario = usuarioRepository.findOne(login);
		return usuario.permissoes();
	}
	
	public Set<Permissao> carregarPermissoesRecurso(String nome, String tipo) {
		return Optional.ofNullable(recursoRepository.findOne(nome, TipoRecurso.valueOf(tipo)))
			.map(recurso -> recurso.permissoesExigidas()).orElse(Collections.emptySet());
	}
	
	public Set<Papel> carregarPapeisUsuario(String login) {
		Usuario usuario = usuarioRepository.findOne(login);
		return usuario.papeis();
	}
	
	public Set<Grupo> carregarGruposUsuario(String login) {
		usuarioRepository.findOne(login);
		return usuarioRepository.findOne(login).grupos();
	}
	
	/**
	 * Registra os grupos e papéis associados a um usuário.
	 * 
	 * @param id Id do usuário.
	 * @param papeis Conjunto de papéis
	 * @param grupos
	 */
	public void configurarPermissoesUsuario(Long id, Set<Long> papeisAdicionados, Set<Long> gruposAdicionados, Set<Long> papeisRemovidos, Set<Long> gruposRemovidos) {
		
		Set<Papel> papeisAdic = new HashSet<Papel>();
		Set<PapelId> papeisRemov = new HashSet<PapelId>();
		Set<Grupo> gruposAdic = new HashSet<Grupo>();
		Set<GrupoId> gruposRemov = new HashSet<GrupoId>();
		
		Optional.ofNullable(papeisRemovidos).ifPresent(p2 -> p2.forEach(p -> papeisRemov.add(new PapelId(p))));
		Optional.ofNullable(papeisAdicionados).ifPresent(g2 -> g2.forEach(g -> gruposRemov.add(new GrupoId(g))));
		Optional.ofNullable(papeisAdicionados).ifPresent(p1 -> p1.forEach(p -> papeisAdic.add(this.papelRepository.findOne(new PapelId(p)))));
		Optional.ofNullable(gruposAdicionados).ifPresent(g1 -> g1.forEach(g -> gruposAdic.add(this.grupoRepository.findOne(new GrupoId(g)))));
				
		Usuario usuario = this.usuarioRepository.findOne(new UsuarioId(id));
		usuario.removerPapeis(papeisRemov);
		usuario.removerGrupos(gruposRemov);
		usuario.atribuirPapeis(papeisAdic);
		usuario.atribuirGrupos(gruposAdic);
		
		this.usuarioRepository.save(usuario);
	}
	
	/**
	 * Recupera as informações do usuário.
	 * 
	 * @param login Login do usuário.
	 * @return Informações do usuário.
	 */
	public Usuario recuperarInformacoesUsuario(String login){
		return usuarioRepository.findOne(login);
	}

}
