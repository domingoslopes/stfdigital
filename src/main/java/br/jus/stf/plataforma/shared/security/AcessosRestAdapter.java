package br.jus.stf.plataforma.shared.security;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.acessos.interfaces.AcessosRestResource;
import br.jus.stf.plataforma.acessos.interfaces.dto.UsuarioDto;
import br.jus.stf.plataforma.shared.security.resource.ResourceImpl;
import br.jus.stf.plataforma.shared.security.resource.ResourceType;
import br.jus.stf.plataforma.shared.security.stereotype.Resource;
import br.jus.stf.shared.PessoaId;
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
	
	public Set<Resource> carregarRecursosUsuario(String login) {
		return acessosRestResouce.recursos(login).stream()
				.map(recurso -> new ResourceImpl(recurso.getNome(), ResourceType.valueOf(recurso.getTipo())))
				.collect(Collectors.toSet());
	}
	
	public Set<String> carregarPapeisUsuario(String login) {
		return acessosRestResouce.papeis(login).stream()
				.map(p -> p.getNome()).collect(Collectors.toSet());
	}
	
	public Set<String> carregarPapeisRecurso(String recurso, String tipoRecurso) {
		return acessosRestResouce.papeis(recurso, tipoRecurso).stream()
				.map(p -> p.getNome()).collect(Collectors.toSet());
	}
	
	public Optional<UserDetails> recuperarUsuario(UsuarioId usuarioId) {
		return Optional.ofNullable(acessosRestResouce.recuperarUsuario(usuarioId.toLong()))
				.map(dto -> newUser(dto)).orElse(Optional.empty());
	}
	
	public Optional<UserDetails> recuperarUsuario(String login) {
		return Optional.ofNullable(acessosRestResouce.recuperarUsuario(login))
				.map(dto -> newUser(dto)).orElse(Optional.empty());
	}
	
	/**
	 * @param dto
	 * @return
	 */
	private Optional<UserDetails> newUser(UsuarioDto dto) {
		UsuarioId usuarioId = new UsuarioId(dto.getId());
		PessoaId pessoaId = new PessoaId(dto.getPessoaId());
		return Optional.of(new UserDetails(usuarioId, pessoaId, dto.getNome()));
	}
	
}
