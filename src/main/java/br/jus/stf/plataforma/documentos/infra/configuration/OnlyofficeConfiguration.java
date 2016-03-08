package br.jus.stf.plataforma.documentos.infra.configuration;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Configurações necessárias para a integração com o onlyoffice.
 * 
 * @author Tomas.Godoi
 *
 */
@Configuration
public class OnlyofficeConfiguration {

	@Value("${http.client.ssl.trust-store}")
	private String trustStore;

	@Value("${http.client.ssl.trust-store-password}")
	private char[] trustStorePassword;

	@Bean(name = "onlyofficeRestTemplate")
	public RestTemplate onlyofficeRestTemplate() throws Exception {
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		messageConverters.add(new ByteArrayHttpMessageConverter());

		RestTemplate restTemplate = new RestTemplate(messageConverters);

		return restTemplate;
	}

	@PostConstruct
	public void configureTrustStore() throws Exception {
		String keystoreType = "JKS";
		InputStream keystoreLocation = null;
		char[] keystorePassword = null;
		char[] keyPassword = null;

		KeyStore keystore = KeyStore.getInstance(keystoreType);
		keystore.load(keystoreLocation, keystorePassword);
		KeyManagerFactory kmfactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		kmfactory.init(keystore, keyPassword);

		InputStream truststoreLocation = new FileInputStream(trustStore);
		char[] truststorePassword = trustStorePassword;
		String truststoreType = "JKS";

		KeyStore truststore = KeyStore.getInstance(truststoreType);
		truststore.load(truststoreLocation, truststorePassword);
		TrustManagerFactory tmfactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		tmfactory.init(truststore);

		KeyManager[] keymanagers = kmfactory.getKeyManagers();
		TrustManager[] trustmanagers = tmfactory.getTrustManagers();

		SSLContext sslContext = SSLContext.getInstance("TLS");
		sslContext.init(keymanagers, trustmanagers, new SecureRandom());
		SSLContext.setDefault(sslContext);
	}

}
