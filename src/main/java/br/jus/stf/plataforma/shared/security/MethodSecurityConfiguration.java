package br.jus.stf.plataforma.shared.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

/**
 * @author Lucas.Rodrigues
 *
 */

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {

	@Autowired
	private MethodSecurityExpressionHandler methodSecurityExpressionHandler;
	
	@Override
	protected MethodSecurityExpressionHandler createExpressionHandler() {
		return methodSecurityExpressionHandler;
	}
	
}
