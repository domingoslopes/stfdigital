package br.jus.stf.plataforma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
@ComponentScan("br.jus.stf")
public class ApplicationContextInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationContextInitializer.class, args);
    }
    
    @Bean
    public LocalValidatorFactoryBean validator() {
    	return new LocalValidatorFactoryBean();
    }
    
}
