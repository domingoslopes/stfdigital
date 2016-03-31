package br.jus.stf.core.framework.domaindrivendesign;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 03.03.2016
 */
@FunctionalInterface
public interface DomainEventPublisher {
	
	void publish(DomainEvent<?> event);

}
