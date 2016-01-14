package br.jus.stf.plataforma.shared.security;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * Implementação customizada de informações do usuário
 * 
 * @author Lucas.Rodrigues
 *
 */
public class UserImpl extends User {

	private static final long serialVersionUID = 1L;
	
	private UserDetails userDetails;

	public UserImpl(String login, String password, UserDetails userDetails,
			Collection<? extends GrantedAuthority> authorities) {
		super(login, password, authorities);
		Validate.notNull(userDetails);
		
		this.userDetails = userDetails;
	}

	/**
	 * @return the userDetails
	 */
	public UserDetails getUserDetails() {
		return userDetails;
	}
	
}
