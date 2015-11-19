package br.jus.stf.plataforma.shared.web;

import static br.jus.stf.plataforma.shared.web.CorsFilter.ALLOWED_HEADERS;
import static br.jus.stf.plataforma.shared.web.CorsFilter.ALLOWED_METHODS;
import static br.jus.stf.plataforma.shared.web.CorsFilter.ALLOWED_ORIGINS;
import static br.jus.stf.plataforma.shared.web.CorsFilter.HEADERS;
import static br.jus.stf.plataforma.shared.web.CorsFilter.METHODS;
import static br.jus.stf.plataforma.shared.web.CorsFilter.ORIGINS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

import br.jus.stf.plataforma.shared.web.CorsFilter;

/**
 * Testes unitários para validação do filtro que viabiliza a utilização da 
 * API por clientes em domínios diferentes.
 * 
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 29.07.2015
 * 
 * @see CorsFilter
 */
public class CorsFilterUnitTests {
	
	/**
	 * Apenas valida se todos os headers necessários estão sendo adicionados
	 * ao response pelo CorsFilter
	 */
	@Test
	public void testHeaders() throws IOException, ServletException {
	    HttpServletRequest request = mock(HttpServletRequest.class);
	    HttpServletResponse response = mock(HttpServletResponse.class);
	    FilterChain chain = mock(FilterChain.class);
	    
	    CorsFilter corsFilter = new CorsFilter();
	    corsFilter.doFilter(request, response, chain);
	    
	    verify(response).setHeader(HEADERS, ALLOWED_HEADERS);
		verify(response).setHeader(METHODS, ALLOWED_METHODS);
	    verify(response).setHeader(ORIGINS, ALLOWED_ORIGINS);
	}

}

