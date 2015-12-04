package br.jus.stf.plataforma.shared.certification.infra.configuration;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Configuration;

@Configuration
public class CryptoConfiguration {

	@PostConstruct
	public void configure() {
		CryptoProvider.loadProviders();
	}
	
}
