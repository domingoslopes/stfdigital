package br.jus.stf.plataforma.shared.security;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

/**
 * Classe utilitária com informações do contexto de segurança
 * 
 * @author Lucas.Rodrigues
 *
 */
public class SecurityContextUtil {

	private SecurityContextUtil(){
		
	}
	
	/**
	 * @return a autenticação
	 */
	public static Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	/**
	 * @return o nome do usuário autenticado
	 */
	public static String getUsername() {
		return Optional.ofNullable(getAuthentication())
				.map(auth -> ((User) auth.getPrincipal()).getUsername())
				.orElse(null);
	}
	
	/**
	 * @return as autorizações
	 */
	@SuppressWarnings("unchecked")
	public static Collection<GrantedAuthority> getAuthorities() {
		return (Collection<GrantedAuthority>) Optional.ofNullable(getAuthentication())
				.map(auth -> auth.getAuthorities())
				.orElse(Collections.emptyList());
	}
	
	/**
	 * Verifica se o usuário possui as autorizações informadas
	 * 
	 * @param neededAuthorities
	 * @return true se possui, ou se as autorizações necessárias estiverem vazias, 
	 * ou false caso não contenha todas as autorizações
	 */
	public static boolean userContainsAll(Collection<? extends GrantedAuthority> neededAuthorities) {
		return Optional.ofNullable(neededAuthorities)
				.map(auts -> auts.isEmpty() ? true : getAuthorities().containsAll(auts))
				.orElse(true);
	}
	
}
