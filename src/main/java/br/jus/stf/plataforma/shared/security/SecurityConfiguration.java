package br.jus.stf.plataforma.shared.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
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

	private static final String[] USUARIOS = new String[] {"peticionador", "recebedor", "representante", "autuador", "preautuador", "distribuidor", "cartoraria", "gestor-autuacao"};
	
	@Bean
	public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
	    return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
	}
	
	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}
	
  	@Override
  	public void configure(HttpSecurity http) throws Exception {
  		http.csrf().disable();
  		http.formLogin()
  				.loginPage("/login").defaultSuccessUrl("/", true).permitAll().and()
  			.logout()
  				.logoutUrl("/logout").deleteCookies("JSESSIONID").permitAll().and()
  			.authorizeRequests()
  				.antMatchers("/**").authenticated().and()
  			.csrf()
  				.csrfTokenRepository(csrfTokenRepository()).and()
  			.addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class)
  			.exceptionHandling()
  				.authenticationEntryPoint(new SecurityAuthenticationEntryPoint("/login"))
  				.accessDeniedPage("/login").and()
  			.sessionManagement()
  				.maximumSessions(1).expiredUrl("/login").sessionRegistry(sessionRegistry());
  	}
  	
  	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/theme/**", "/vendor/**", "/tmp/**");
	}
  	
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    	InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> builder = auth.inMemoryAuthentication();
    	Arrays.asList(USUARIOS).forEach(u -> builder.withUser(u).password("123").authorities(u, "servidor"));
    }
  
	private CsrfTokenRepository csrfTokenRepository() {
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setHeaderName("X-XSRF-TOKEN");
		return repository;
	}
}
