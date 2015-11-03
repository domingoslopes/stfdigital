package br.jus.stf.plataforma.shared.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Criado provisoriamente para viabilizar o funcionamento básico dos Mecanismo de Workflow e do Mecanismo de Ações, que
 * demandam a presença do papel do usuário para filtrar tarefas e ações, respectivamente.
 * 
 * [TODO] Remover essa classe substituindo pelo Mecanismo de Segurança
 * 
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 18.09.2015
 */
@Component
public class AuthoritiesMockFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		Optional<String> papel = Optional.ofNullable(request.getHeader("papel"));
		
		if (papel.isPresent()) {
			AnonymousAuthenticationToken authentication = new AnonymousAuthenticationToken(papel.get(), papel.get(), Arrays.asList(new SimpleGrantedAuthority(papel.get())));
	
	        SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());
	        SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		
		filterChain.doFilter(request, response);
	}
}