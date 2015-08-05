package br.jus.stf.plataforma.web;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.jus.stf.plataforma.settings.Profiles;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 27.7.2015
 */
@Configuration
@Profile(value = Profiles.PRODUCAO)
public class ServerConfiguration extends ServerProperties {
    
    private static final int PRODUCTION_PORT = 80;

	/**
     * @see org.springframework.boot.autoconfigure.web.ServerProperties#customize(org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer)
     */
    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        container.setPort(PRODUCTION_PORT);
    }
    
}
