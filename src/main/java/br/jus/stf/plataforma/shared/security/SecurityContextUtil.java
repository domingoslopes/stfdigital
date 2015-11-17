package br.jus.stf.plataforma.shared.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

/**
 * Classe utilitária com informações do contexto de segurança
 * 
 * @author Lucas.Rodrigues
 *
 */
public class SecurityContextUtil {

	
	/**
	 * @return a autenticação
	 */
	public static Authentication getAutenticacao() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	/**
	 * @return o nome do usuário autenticado
	 */
	public static String getNomeUsuario() {
		return Optional.ofNullable(getAutenticacao())
				.map(auth -> ((User) auth.getPrincipal()).getUsername())
				.orElse(null);
	}
	
	/**
	 * @return as autorizações
	 */
	public static List<String> getAutorizacoes() {
		List<String> autorizacoes = new ArrayList<String>();
		Optional.ofNullable(getAutenticacao())
			.ifPresent(autenticacao -> autenticacao.getAuthorities()
				.forEach(aut -> autorizacoes.add(aut.getAuthority())));
		return autorizacoes;
	}
	
}
