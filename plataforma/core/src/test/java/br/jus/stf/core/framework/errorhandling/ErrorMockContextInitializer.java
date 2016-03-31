package br.jus.stf.core.framework.errorhandling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 21.12.2015
 */
@SpringBootApplication
public class ErrorMockContextInitializer {
	
	public static void main(String[] args) {
		SpringApplication.run(ErrorMockContextInitializer.class, args);
	}
	
}
