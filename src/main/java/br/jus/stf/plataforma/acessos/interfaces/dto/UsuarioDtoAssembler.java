package br.jus.stf.plataforma.acessos.interfaces.dto;

import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

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
	
	public UsuarioDto toDto(String nome, String setor, Set<PapelDto> papeis, Set<GrantedAuthority> authorities) {		
		return new UsuarioDto(nome, "", papeis, authorities);
	}
}
