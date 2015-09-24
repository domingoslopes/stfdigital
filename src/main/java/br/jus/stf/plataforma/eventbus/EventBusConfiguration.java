package br.jus.stf.plataforma.eventbus;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import reactor.Environment;
import reactor.bus.EventBus;

/**
 * Configuração do barramento de eventos
 * 
 * @author Lucas.Rodrigues
 *
 */
@Configuration
public class EventBusConfiguration {

	@Bean
    Environment reactorEnvironment() {
        return Environment.initializeIfEmpty().assignErrorJournal();
    }
	
    @Bean
    EventBus eventBus() {
	    //return EventBus.create(reactorEnvironment(), Environment.sharedDispatcher());
    	return EventBus.create(); //síncrono por causa do entitymanager compartilhado entre os contextos
    }
	
}
