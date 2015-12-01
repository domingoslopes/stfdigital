package br.jus.stf.plataforma.shared.tests;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.jus.stf.plataforma.shared.security.AcessosRestAdapter;

/**
 * Criado provisoriamente para viabilizar o funcionamento básico dos Mecanismo de Workflow e do Mecanismo de Ações, que
 * demandam a presença do papel do usuário para filtrar tarefas e ações, respectivamente.
 * 
 * [TODO] Remover essa classe substituindo pelo Mecanismo de SeguranÃ§a
 * 
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 18.09.2015
 */
@Component
public class AuthoritiesMockFilter extends OncePerRequestFilter {
	
	@Autowired
	AcessosRestAdapter acessoAdapter;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		Optional<String> login = Optional.ofNullable(request.getHeader("login"));
		
		if (login.isPresent()) {
			Set<GrantedAuthority> authorities = acessoAdapter.carregarPermissoesUsuario(login.get());
			User principal = new User(login.get(), "N/A", authorities);
			AnonymousAuthenticationToken authentication = new AnonymousAuthenticationToken(login.get(), principal, authorities);
	
	        SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());
	        SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		
		filterChain.doFilter(request, response);
	}
}
