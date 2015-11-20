package br.jus.stf.plataforma.acessos.application;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.jus.stf.plataforma.acessos.domain.model.Permissao;
import br.jus.stf.plataforma.acessos.domain.model.TipoRecurso;
import br.jus.stf.plataforma.acessos.domain.model.Usuario;
import br.jus.stf.plataforma.acessos.domain.model.UsuarioRepository;

/**
 * @author Lucas.Rodrigues
 *
 */
@Service
@Transactional
public class AcessosApplicationService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Set<Permissao> carregarPermissoesUsuario(String login) {
		Usuario usuario = usuarioRepository.findOne(login);
		return usuario.permissoes();
	}
	
	public Set<Permissao> carregarPermissoesRecurso(String nome, String tipoRecurso) {
		return Optional.ofNullable(usuarioRepository.findOneRecurso(nome, TipoRecurso.valueOf(tipoRecurso)))
			.map(recurso -> recurso.permissoesExigidas()).orElse(Collections.emptySet());
	}
	
	public Set<String> carregarPapeisUsuario(String login) {
		Usuario usuario = usuarioRepository.findOne(login);
		return usuario.papeis().stream()
				.map(papel -> papel.nome())
				.collect(Collectors.toSet());
	}

}
