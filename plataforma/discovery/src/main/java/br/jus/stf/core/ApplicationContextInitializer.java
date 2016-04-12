package br.jus.stf.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 05.04.2016
 */
@SpringBootApplication
@EnableEurekaServer
public class ApplicationContextInitializer {
	
	public static void main(String[] args) {
		SpringApplication.run(ApplicationContextInitializer.class, args);
	}

}
