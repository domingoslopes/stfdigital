package br.jus.stf.plataforma.shared.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import br.jus.stf.plataforma.shared.web.CsrfHeaderFilter;

/**
 * @author Lucas.Rodrigues
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private static final String[] USUARIOS = new String[] {"peticionador", "recebedor", "autuador", "preautuador", "distribuidor"};
	
	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}
	
  	@Override
  	public void configure(HttpSecurity http) throws Exception {  		
  		http.formLogin()
  				.loginPage("/login").permitAll().and()
  			.logout()
  				.logoutUrl("/logout").permitAll().and()
  			.authorizeRequests()
  				.anyRequest().authenticated().and()
  			.csrf()
  				.csrfTokenRepository(csrfTokenRepository()).and()
  			.addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class)
  			.exceptionHandling()
  				.authenticationEntryPoint(new SecurityAuthenticationEntryPoint("/login")).and()
  			.sessionManagement()
  				.sessionCreationPolicy(SessionCreationPolicy.NEVER)
  				.maximumSessions(1).maxSessionsPreventsLogin(true).expiredUrl("/login").sessionRegistry(sessionRegistry());
  	}
  	
  	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/theme/**", "/vendor/**", "/tmp/**");
	}
  	
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    	InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> builder = auth.inMemoryAuthentication();
    	Arrays.asList(USUARIOS).forEach(u -> builder.withUser(u).password("123").roles(u, "servidor"));
    }
  
	private CsrfTokenRepository csrfTokenRepository() {
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setHeaderName("X-XSRF-TOKEN");
		return repository;
	}
}
