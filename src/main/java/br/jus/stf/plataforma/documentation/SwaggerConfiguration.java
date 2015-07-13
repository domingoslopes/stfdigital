package br.jus.stf.plataforma.documentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 10.07.2015
 */
@Configuration
@EnableSwagger
@EnableAutoConfiguration
public class SwaggerConfiguration {

	@Autowired
	private SpringSwaggerConfig springSwaggerConfig;

	@Bean
	public SwaggerSpringMvcPlugin customImplementation() {
		SwaggerSpringMvcPlugin swaggerSpringMvcPlugin = new SwaggerSpringMvcPlugin(springSwaggerConfig);
		
		swaggerSpringMvcPlugin.apiInfo(new ApiInfo(
			"STF API", 
			"API do STF Digital", 
			"digital.stf.jus.br/uso", 
			"digital@stf.jus.br", 
			"Apache 2.0", 
			"http://www.apache.org/licenses/LICENSE-2.0.html"
		));
		
		swaggerSpringMvcPlugin.useDefaultResponseMessages(false);
		
		swaggerSpringMvcPlugin.includePatterns("/api/.*");
		
		return swaggerSpringMvcPlugin;
	}

}