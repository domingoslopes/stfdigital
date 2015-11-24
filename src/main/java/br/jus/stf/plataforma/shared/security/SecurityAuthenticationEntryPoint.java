package br.jus.stf.plataforma.shared.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

/**
 * Ponto de entrada de autenticação customizado para tratar as requisições ajax 
 * 
 * @author Lucas.Rodrigues
 *
 */
public class SecurityAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {
	public SecurityAuthenticationEntryPoint(String loginFormUrl) {
		super(loginFormUrl);
	}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
		
		String xrequestedWith = request.getHeader("X-Requested-With");
		
		if (xrequestedWith != null && "XMLHttpRequest".equals(xrequestedWith) && authException != null) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		} else {
			super.commence(request, response, authException);
		}
	}
}
