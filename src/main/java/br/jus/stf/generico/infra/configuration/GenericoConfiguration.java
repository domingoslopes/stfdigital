package br.jus.stf.generico.infra.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * @author Lucas.Rodrigues
 *
 */
@Configuration
public class GenericoConfiguration {

	@Bean
	public CommonsMultipartResolver commonsMultipartResolver() {
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		commonsMultipartResolver.setMaxUploadSize(10485760);
		return commonsMultipartResolver;
	}
	
}
