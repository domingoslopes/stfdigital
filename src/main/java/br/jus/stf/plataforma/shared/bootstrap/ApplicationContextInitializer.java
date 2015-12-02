package br.jus.stf.plataforma.shared.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 17.06.2015
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude = ElasticsearchAutoConfiguration.class)
@ComponentScan("br.jus.stf")
public class ApplicationContextInitializer {
    
    @Bean
    public LocalValidatorFactoryBean validator() {
    	return new LocalValidatorFactoryBean();
    }
    
    public static void main(String[] args) {
        SpringApplication.run(ApplicationContextInitializer.class, args);
    }
    
}
