package br.jus.stf.plataforma.shared.web;

import static java.util.Arrays.asList;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 
 * Filtro para fazer o bypass do CRSF nas URLs do onlyoffice, pois estava causando problemas.
 * 
 * TODO Remover esse filtro quando o onlyoffice estiver preparado para utilizar o CRSF.
 * 
 * @author Tomas.Godoi
 *
 */
public class StfCsrfFilter extends OncePerRequestFilter {

	private List<RequestMatcher> allowedResources = asList(new AntPathRequestMatcher("/api/onlyoffice/**"));

	private CsrfTokenRepository tokenRepository;

	public StfCsrfFilter(CsrfTokenRepository tokenRepository) {
		this.tokenRepository = tokenRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	        throws ServletException, IOException {
		if (matches(request, allowedResources)) {
			CsrfToken token = tokenRepository.generateToken(request);
			AddParamsToHeader wrappedRequest = new AddParamsToHeader(request, token.getToken());
			request.getSession(true).setAttribute(
			        "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN", token);
			filterChain.doFilter(wrappedRequest, response);
		} else {
			filterChain.doFilter(request, response);
		}
	}

	private boolean matches(HttpServletRequest request, List<RequestMatcher> resources) {
		return resources.stream().anyMatch(m -> m.matches(request));
	}

	private class AddParamsToHeader extends HttpServletRequestWrapper {

		private String token;
		private static final String tokenName = "X-XSRF-TOKEN";

		public AddParamsToHeader(HttpServletRequest request, String token) {
			super(request);
			this.token = token;
		}

		public String getHeader(String name) {
			if (name.equals(tokenName)) {
				return token;
			}
			String header = super.getHeader(name);
			return (header != null) ? header : super.getParameter(name);
		}
	}

}
