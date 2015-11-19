package br.jus.stf.plataforma.shared.web;

import static java.util.Arrays.asList;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Para que o modo HTML5 funcione corretamente para todos os casos, precisamos da ajuda do servidor
 * responsável por servir os recursos ao navegador. Resumidamente, nós precisamos configurar redirecionamentos
 * no servidor para que qualquer resiquição por qualquer recurso seja respondida com a página de entrada 
 * da aplicação (aquela que contém o diretiva ng-app: single-page web application's starting page).
 * 
 * <p>Para entender por que esses redirecionamentos são necessários, vamos pegar o exemplo em que o autuador
 * usa uma URL (em modo HTML5) apontando para os documentos relacionados à petição de código '50547'. 
 * Essa URL pode ser como a que se segue: http://digital.stf.jus.br/peticoes/50547/documentos 
 * Para o navegador, essa é uma URL como qualquer outra qualquer e ele precisará enviar uma requisição 
 * para o servidor. Em uma aplicação Web de uma única página, tal URL não faz sentido para o servidor, apenas 
 * para o cliente. O recurso /peticoes/50547/documentos não existe fisicamente no servidor e não podem ser 
 * gerado pelo servidor de forma dinâmica. A única coisa que um servidor pode fazer com essa URL é 
 * para redirecioná-la para o ponto de aplicação de partida. Isto irá resultar no recarregamento 
 * na aplicação no navegador. Quando o aplicativo é recarregado, o serviço $location vai pegar a
 * URL (ainda presente na barra de endereços do navegador), e é aí que o processamento do lado 
 * do cliente pode assumir.
 * 
 * <p>No servidor, o que temos que fazer é configurar os redirecionamentos para 3 tipos de recursos:
 *  <br>- URLs apontando para recursos estáticos (images, CSS's, AngularJS partials, e etc)
 *  <br>- URLs para recuperação ou alteração de dados (por exemplo, uma API RESTful)
 *  <br>- URLs que representam funcionalidades da aplicação em modo HTML5, em que o servidor deve
 * responder com a página de destino (uma URL da lista de favoritas, ou uma URL digitada
 * na barra de endereços do browser).
 * 
 * <p>Sabendo que é complicado enumerar todas as URLs que podem chegar ao servidor web, devido 
 * às possibilidade do uso do modo HTML5, a melhor alternativa é usar um prefixo conhecido, 
 * tanto para recursos estáticos quanto para as URLs usadas para manipular dados. Esta é a 
 * estratégia utilizada aqui, onde todos os recursos estáticos são servidos a partir das 
 * URLs com os prefixos /application, /theme, /tmp e /vendor, enquanto o prefixo /api é reservado 
 * para a manipulação de dados no back-end. A maioria das URLs restantes são redirecionados 
 * para o ponto de partida da aplicação (index.html).
 * 
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 29.07.2015
 */
@Component
public class Html5Filter extends OncePerRequestFilter {

	/**
	 * A lista de padrões para URL's dos recursos estáticos servidos pelo sistema
	 */
	private List<RequestMatcher> staticResources = asList(
		new AntPathRequestMatcher("/application/**"), 
		new AntPathRequestMatcher("/theme/**"), 
		new AntPathRequestMatcher("/tmp/**"), 
		new AntPathRequestMatcher("/vendor/**"),
		new AntPathRequestMatcher("/api-docs/**"),
		new AntPathRequestMatcher("/apidocs/**"),
		new AntPathRequestMatcher("/login"),
		new AntPathRequestMatcher("/logout"),
		new AntPathRequestMatcher("/")
	);
	
	/**
	 * A lista de padrões para URL's dos recursos dinâmicos servidos pelo sistema
	 */
	private List<RequestMatcher> dynamicResources = asList(
		new AntPathRequestMatcher("/api/**")
	);
		
    /**
     * Aplica a estratégia descrita acima: repassa ao servidor requisições que ele pode responder ou 
     * redireciona para a raiz da aplicação, para tratamento pelo cliente.
     * 
     * @return o filtro que habita o comportamento descrito acima
     */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (matches(request, staticResources) || matches(request, dynamicResources)) {
            filterChain.doFilter(request, response);
        } else {
            request.getRequestDispatcher("/").forward(request, response);
        }
    }

	/**
	 * Vericar se a URL de uma dada requisição se encaixa em algum dos padrões de URL's definidos por 
	 * uma dada lista de padrões.
	 * 
	 * @param request a requisição enviada pelo cliente
	 * @param resources a lista de padrões para possíveis URL's enviadas pelo cliente
	 * @return true, se a requisição bate com algum padrão de URL, false, caso contrário
	 */
	private boolean matches(HttpServletRequest request, List<RequestMatcher> resources) {
		return resources.stream().anyMatch(m -> m.matches(request));
	}
	
}