package br.jus.stf.plataforma.shared.web;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

import br.jus.stf.plataforma.shared.web.Html5Filter;

/**
 * Testes unitários para validação do filtro que viabiliza, do lado do servidor, a 
 * utilização do modo HTML 5 pelo Angular.
 * 
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 29.07.2015
 * 
 * @see Html5Filter
 */
public class Html5FilterUnitTests {

	/**
	 * Valida o acesso à raiz da aplicação, sem prefixo. Filtro deve repassar a
	 * requisição ao servidor.
	 */
	@Test
	public void testRoot() throws IOException, ServletException {
	    HttpServletRequest request = mock(HttpServletRequest.class);
	    HttpServletResponse response = mock(HttpServletResponse.class);
	    FilterChain chain = mock(FilterChain.class);
	    
	    when(request.getServletPath()).thenReturn("/");
	
	    Html5Filter html5Filter = new Html5Filter();
	    html5Filter.doFilter(request, response, chain);
	    
	    verify(chain).doFilter(request, response);
	}

	/**
	 * Valida o acesso a um recurso estático, prefixo "/application". Filtro deve repassar a
	 * requisição ao servidor.
	 */
	@Test
	public void testStaticResource() throws IOException, ServletException {
	    HttpServletRequest request = mock(HttpServletRequest.class);
	    HttpServletResponse response = mock(HttpServletResponse.class);
	    FilterChain chain = mock(FilterChain.class);
	    
	    when(request.getServletPath()).thenReturn("/application/app.js");
	
	    Html5Filter html5Filter = new Html5Filter();
	    html5Filter.doFilter(request, response, chain);
	    
	    verify(chain).doFilter(request, response);
	}

	/**
	 * Valida o acesso a um recurso dinâmico, prefixo "/api". Filtro deve repassar a
	 * requisição ao servidor.
	 */
	@Test
	public void testDynamicResource() throws IOException, ServletException {
	    HttpServletRequest request = mock(HttpServletRequest.class);
	    HttpServletResponse response = mock(HttpServletResponse.class);
	    FilterChain chain = mock(FilterChain.class);
	    
	    when(request.getServletPath()).thenReturn("/api/tarefas");
	
	    Html5Filter html5Filter = new Html5Filter();
	    html5Filter.doFilter(request, response, chain);
	    
	    verify(chain).doFilter(request, response);
	}

	/**
	 * Valida o acesso a uma URL virtual, que não está no servidor. Filtro deve 
	 * redirecionar para a raiz da aplicação.
	 */
	@Test
	public void testVirtualPath() throws IOException, ServletException {
	    HttpServletRequest request = mock(HttpServletRequest.class);
	    HttpServletResponse response = mock(HttpServletResponse.class);
	    FilterChain chain = mock(FilterChain.class);
	    RequestDispatcher dispatcher = mock(RequestDispatcher.class);
	    
	    when(request.getServletPath()).thenReturn("/peticoes/50547/documentos");
	    when(request.getRequestDispatcher("/")).thenReturn(dispatcher);
	
	    Html5Filter html5Filter = new Html5Filter();
	    html5Filter.doFilter(request, response, chain);
	    
	    verify(dispatcher).forward(request, response);
	}

	/**
	 * Valida o acesso a uma URL virtual, que não está no servidor. Nesse caso uma 
	 * URL sem a segunda barra. Filtro deve redirecionar para a raiz da 
	 * aplicação.
	 */
	@Test
	public void testSingleVirtualPath() throws IOException, ServletException {
	    HttpServletRequest request = mock(HttpServletRequest.class);
	    HttpServletResponse response = mock(HttpServletResponse.class);
	    FilterChain chain = mock(FilterChain.class);
	    RequestDispatcher dispatcher = mock(RequestDispatcher.class);
	    
	    when(request.getServletPath()).thenReturn("/dashboard");
	    when(request.getRequestDispatcher("/")).thenReturn(dispatcher);
	
	    Html5Filter html5Filter = new Html5Filter();
	    html5Filter.doFilter(request, response, chain);
	    
	    verify(dispatcher).forward(request, response);
	}

}

