package br.jus.stf.plataforma.infra.action.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
 * Anotação para as classes controladoras de ação.
 * 
 * @author Lucas.Rodrigues
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Component
public @interface ActionController {

	/**
	 * The value may indicate a suggestion for a logical component name,
	 * to be turned into a Spring bean in case of an autodetected component.
	 * @return the suggested component name, if any
	 */
	String value() default "";
	
	/**
	 * The context indicate a location for the action searcher mechanism. 
	 * 
	 * @return the context
	 */
	String context();
}
