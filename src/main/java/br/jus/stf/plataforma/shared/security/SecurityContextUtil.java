package br.jus.stf.plataforma.shared.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

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
	public static UserImpl getUser() {
		return Optional.ofNullable(getAuthentication())
				.map(auth -> ((UserImpl) auth.getPrincipal()))
				.orElse(null);
	}
	
	/**
	 * @return as autorizações
	 */
	public static Set<GrantedAuthority> getAuthorities() {
		return Optional.ofNullable(getAuthentication())
				.map(auth -> new HashSet<GrantedAuthority>(auth.getAuthorities()))
				.orElse(new HashSet<GrantedAuthority>(0));
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
