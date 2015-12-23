package br.jus.stf.plataforma.shared.security;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.common.lang3.Validate;

import br.jus.stf.shared.UsuarioId;

/**
 * @author Lucas.Rodrigues
 *
 */
public class UserDetails {

	private UsuarioId userId;
	private String name;
	private List<String> roles = new ArrayList<String>(0);
	
	public UserDetails(UsuarioId userId, String name) {
		
		Validate.notNull(userId);
		Validate.notBlank(name);
		
		this.userId = userId;
		this.name = name;
	}

	/**
	 * @return identificador do usuário
	 */
	public UsuarioId getUserId() {
		return userId;
	}
	
	/**
	 * @return nome completo do usuário
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return os papéis do usuário
	 */
	public List<String> getRoles() {
		return roles;
	}
	
}
