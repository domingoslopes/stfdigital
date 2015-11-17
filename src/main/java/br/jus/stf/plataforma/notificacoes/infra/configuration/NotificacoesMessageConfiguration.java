package br.jus.stf.plataforma.notificacoes.infra.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.StompWebSocketEndpointRegistration;

import br.jus.stf.plataforma.shared.settings.Profiles;

/**
 * @author Lucas.Rodrigues
 *
 */
@Configuration
@EnableScheduling
@EnableWebSocketMessageBroker
public class NotificacoesMessageConfiguration extends AbstractWebSocketMessageBrokerConfigurer {
	
	@Autowired
	private Environment env;
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		StompWebSocketEndpointRegistration reg = registry.addEndpoint("/api/ws/notificacoes");
		if (env.acceptsProfiles(Profiles.DESENVOLVIMENTO)) {
			reg.setAllowedOrigins("*");
		}
		reg.withSockJS();
	}

}
