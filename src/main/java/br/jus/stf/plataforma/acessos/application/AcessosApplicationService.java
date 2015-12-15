package br.jus.stf.plataforma.acessos.application;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.jus.stf.plataforma.acessos.domain.model.Grupo;
import br.jus.stf.plataforma.acessos.domain.model.GrupoRepository;
import br.jus.stf.plataforma.acessos.domain.model.Papel;
import br.jus.stf.plataforma.acessos.domain.model.PapelRepository;
import br.jus.stf.plataforma.acessos.domain.model.Permissao;
import br.jus.stf.plataforma.acessos.domain.model.RecursoRepository;
import br.jus.stf.plataforma.acessos.domain.model.TipoGrupo;
import br.jus.stf.plataforma.acessos.domain.model.TipoRecurso;
import br.jus.stf.plataforma.acessos.domain.model.Usuario;
import br.jus.stf.plataforma.acessos.domain.model.UsuarioRepository;
import br.jus.stf.plataforma.identidades.application.PessoaApplicationEvent;
import br.jus.stf.plataforma.identidades.application.UsuarioApplicationEvent;
import br.jus.stf.plataforma.identidades.domain.model.Pessoa;
import br.jus.stf.plataforma.identidades.domain.model.PessoaRepository;
import br.jus.stf.shared.GrupoId;
import br.jus.stf.shared.PapelId;
import br.jus.stf.shared.PessoaId;
import br.jus.stf.shared.UsuarioId;

/**
 * @author Lucas.Rodrigues
 *
 */
@Service
@Transactional
public class AcessosApplicationService {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private RecursoRepository recursoRepository;
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private PapelRepository papelRepository;
	
	@Autowired
	private PessoaApplicationEvent pessoaApplicationEvent;
	
	@Autowired
	private UsuarioApplicationEvent usuarioApplicationEvent;
	
	public Set<Permissao> carregarPermissoesUsuario(String login) {
		return Optional.ofNullable(usuarioRepository.findOne(login))
				.map(usuario -> usuario.permissoes())
				.orElse(Collections.emptySet());
	}
	
	public Set<Permissao> carregarPermissoesRecurso(String nome, String tipo) {
		return Optional.ofNullable(recursoRepository.findOne(nome, TipoRecurso.valueOf(tipo)))
				.map(recurso -> recurso.permissoesExigidas())
				.orElse(Collections.emptySet());
	}
	
	public Set<Papel> carregarPapeisUsuario(String login) {
		return Optional.ofNullable(usuarioRepository.findOne(login))
				.map(usuario -> usuario.papeis())
				.orElse(Collections.emptySet());
	}
	
	public Set<Grupo> carregarGruposUsuario(String login) {
		return Optional.ofNullable(usuarioRepository.findOne(login))
				.map(usuario -> usuario.grupos())
				.orElse(Collections.emptySet());
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
		Optional.ofNullable(gruposRemovidos).ifPresent(g2 -> g2.forEach(g -> gruposRemov.add(new GrupoId(g))));
		Optional.ofNullable(papeisAdicionados).ifPresent(p1 -> p1.forEach(p -> papeisAdic.add(this.papelRepository.findOne(new PapelId(p)))));
		Optional.ofNullable(gruposAdicionados).ifPresent(g1 -> g1.forEach(g -> gruposAdic.add(this.grupoRepository.findOne(new GrupoId(g)))));
				
		Usuario usuario = usuarioRepository.findOne(new UsuarioId(id));
		usuario.removerPapeis(papeisRemov);
		usuario.removerGrupos(gruposRemov);
		usuario.atribuirPapeis(papeisAdic);
		usuario.atribuirGrupos(gruposAdic);
		
		usuarioRepository.save(usuario);
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
	
	/**
	 * Cadastra um novo usuário
	 * 
	 * @param String login
	 * @param String nome
	 * @param string cpf
	 * @param String oab
	 * @param String email
	 * @param String telefone
	 * 
	 * @return Usuario Usuário criado
	 */
	public Usuario cadastrarUsuario(String login, String nome, String cpf, String oab, String email, String telefone) {
		PessoaId idPessoa = pessoaRepository.nextId();
		Pessoa pessoa;
		
		if (StringUtils.isNotBlank(cpf) && StringUtils.isNotBlank(oab) && StringUtils.isNotBlank(email) && StringUtils.isNotBlank(telefone)) {
			pessoa = new Pessoa(idPessoa, nome, cpf, oab, email, telefone);
			
		} else if (StringUtils.isNotBlank(cpf) && StringUtils.isNotBlank(email) && StringUtils.isNotBlank(telefone)) {
			pessoa = new Pessoa(idPessoa, nome, cpf, email, telefone);
			
		} else if (StringUtils.isNotBlank(cpf)) {
			pessoa = new Pessoa(idPessoa, nome, cpf);
			
		} else {
			pessoa = new Pessoa(idPessoa, nome);
		}
		
		pessoaRepository.save(pessoa);
		
		UsuarioId idUsuario = new UsuarioId(idPessoa.toLong());
		Usuario principal = new Usuario(idUsuario, pessoa, login);

		this.usuarioRepository.save(principal);
		this.pessoaApplicationEvent.pessoaCadastrada(pessoa);
				
		Grupo grupoUsuario = grupoRepository.findOne("usuario", TipoGrupo.CONFIGURACAO);
		Set<Grupo> grupos = new HashSet<Grupo>();
		grupos.add(grupoUsuario);
		
		principal.atribuirGrupos(grupos);		
		
		usuarioRepository.save(principal);
		pessoaApplicationEvent.pessoaCadastrada(pessoa);
		
		return principal;
	}

}
