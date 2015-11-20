package br.jus.stf.plataforma.shared.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

/**
 * @author Lucas.Rodrigues
 *
 */
@Configuration
public class WebSocketSecurityConfiguration extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages.nullDestMatcher().authenticated()
        		.simpSubscribeDestMatchers("/user/**").hasAuthority("VISUALIZAR_NOTIFICACAO")
                .anyMessage().denyAll();

    }
}
