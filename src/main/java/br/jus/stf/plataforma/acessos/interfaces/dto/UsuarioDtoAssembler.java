package br.jus.stf.plataforma.acessos.interfaces.dto;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.acessos.domain.model.Usuario;

/**
 * Classe responsável por criar um objeto UsuarioDto.
 * 
 * @author Anderson.Araujo
 * 
 * @since 30.11.2015
 *
 */
@Component
public class UsuarioDtoAssembler {
	
	@Autowired
	private PapelDtoAssembler papelDtoAssembler;
	
	public UsuarioDto toDto(Usuario usuario, Set<GrantedAuthority> authorities) {		
		Set<PapelDto> papeis = usuario.papeis().stream().map(papel -> this.papelDtoAssembler.toDto(papel)).collect(Collectors.toSet());
		return new UsuarioDto(usuario.login(), usuario.pessoa().nome(), usuario.setor(), papeis, authorities);
	}
}
