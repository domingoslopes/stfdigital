package br.jus.stf.plataforma.shared.security;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import br.jus.stf.plataforma.shared.web.CsrfHeaderFilter;

/**
 * @author Lucas.Rodrigues
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter { 
	
	@Autowired
	private AuthenticationProvider authenticationProvider;
	
	@Bean
	public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
	    return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
	}
	
	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}
	
	@Bean
	public FilterRegistrationBean securityFilterChain(
			@Qualifier(AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME) Filter securityFilter) {
	    FilterRegistrationBean registration = new FilterRegistrationBean(securityFilter);
	    registration.setOrder(Integer.MIN_VALUE);
	    registration.setName(AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME);
	    return registration;
	}
	
  	@Override
  	public void configure(HttpSecurity http) throws Exception {
  		
  		final String LOGIN = "/login";
  		
  		http.csrf().disable();
  		http.formLogin()
  				.loginPage(LOGIN).defaultSuccessUrl("/", true).permitAll().and()
  			.logout()
  				.logoutUrl("/logout").deleteCookies("JSESSIONID").permitAll().and()
  			.authorizeRequests()
  				.antMatchers("/cadastro", "/api/acessos/usuarios").anonymous()
  				.antMatchers("/login", "/application/**").permitAll()
				.antMatchers("/**").authenticated()
  				.and()
  			.csrf()
  				.csrfTokenRepository(csrfTokenRepository()).and()
  			.addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class)
  			.exceptionHandling()
  				.authenticationEntryPoint(new SecurityAuthenticationEntryPoint(LOGIN))
  				.accessDeniedPage(LOGIN).and()
  			.sessionManagement()
  				.maximumSessions(1).expiredUrl(LOGIN).sessionRegistry(sessionRegistry());
  	}
  	
  	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/theme/**", "/vendor/**", "/tmp/**", "/application/**");
	}
  	
  	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.authenticationProvider(authenticationProvider);
    }
  
	private CsrfTokenRepository csrfTokenRepository() {
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setHeaderName("X-XSRF-TOKEN");
		return repository;
	}
}
