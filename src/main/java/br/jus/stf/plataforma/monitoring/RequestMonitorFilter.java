package br.jus.stf.plataforma.monitoring;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Monitora as requisições dinâmicas encaminhadas ao sistema.
 * 
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0.M4
 * @since 16.10.2015
 */
@Component
public class RequestMonitorFilter extends OncePerRequestFilter {

	/**
	 * Padrão para URL's dos recursos dinâmicos servidos pelo sistema
	 */
	private static final RequestMatcher DYNAMIC_RESOURCES = new AntPathRequestMatcher("/api/**");
	
	@Autowired
	private RequestMonitor requestMonitor;
	
    /**
     * Aplica a estratégia descrita acima: repassa ao servidor requisições que ele pode responder ou 
     * redireciona para a raiz da aplicação, para tratamento pelo cliente.
     * 
     * @return o filtro que habita o comportamento descrito acima
     */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (isDynamicResource(request)) {
        	MonitoredRequest monitoredRequest = new MonitoredRequest(request, response, filterChain);
        	
        	requestMonitor.monitor(monitoredRequest);
        } else {
            filterChain.doFilter(request, response);
        }
    }
	
	/**
	 * Vericar se a URL de uma dada requisição se encaixa no padrão das URL's dos recursos dinâmicos.
	 * 
	 * @param request a requisição enviada pelo cliente
	 * @return true, se a requisição bate com algum padrão de URL, false, caso contrário
	 */
	private boolean isDynamicResource(HttpServletRequest request) {
		return DYNAMIC_RESOURCES.matches(request) && !request.getMethod().equals("OPTIONS");
	}
	
}