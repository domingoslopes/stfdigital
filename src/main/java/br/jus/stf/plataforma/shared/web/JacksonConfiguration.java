package br.jus.stf.plataforma.shared.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/** 
 * @author Gabriel.Bastos
 *
 */

@Configuration
public class JacksonConfiguration {

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true);
		return mapper;
	}
	
}
