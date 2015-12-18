package br.jus.stf.plataforma.shared.security;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.acessos.interfaces.AcessosRestResource;
import br.jus.stf.shared.UsuarioId;

/**
 * @author Lucas.Rodrigues
 *
 */
@Component
public class AcessosRestAdapter {

	@Autowired
	private AcessosRestResource acessosRestResouce;
	
	public Set<GrantedAuthority> carregarPermissoesUsuario(String login) {
		return acessosRestResouce.permissoes(login).stream()
				.map(permissao -> new SimpleGrantedAuthority(permissao.getDescricao()))
				.collect(Collectors.toSet());	
	}
	
	public Set<GrantedAuthority> carregarPermissoesRecurso(String nome, String tipoRecurso) {
		return acessosRestResouce.permissoes(nome, tipoRecurso).stream()
				.map(permissao -> new SimpleGrantedAuthority(permissao.getDescricao()))
				.collect(Collectors.toSet());
	}
	
	public Set<String> carregarPapeisUsuario(String login) {
		return acessosRestResouce.papeis(login).stream().map(p -> p.getNome()).collect(Collectors.toSet());
	}
	
	public String recuperarNomeUsuario(UsuarioId usuarioId) {
		return acessosRestResouce.recuperarUsuario(usuarioId.toLong()).getNome();
	}
	
}
