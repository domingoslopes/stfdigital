package br.jus.stf.plataforma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

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
    
}
