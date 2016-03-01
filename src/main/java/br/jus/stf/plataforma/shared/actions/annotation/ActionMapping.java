package br.jus.stf.plataforma.shared.actions.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.jus.stf.plataforma.shared.actions.support.ResourcesMode;

/**
 * Indica que a classe anotada é uma Ação.
 * 
 * @author Rodrigo.Barreiros
 * @since 24.05.2010
 * 
 * @see ActionController
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface ActionMapping {
	
    /**
     * O identificador da ação. A ação pode ser acessada na camada de apresentação usando
     * esse identificador
     * @return id da ação
     */
    String id();
    
    
    /**
     * O nome da ação. É esse nome que será apresentado no menu de ações
     * @return o nome da ação
     */
    String name();
    
    /**
     * Modo que indica a quantidade de recursos para a ação ser executada
     * 
     * @return
     */
    ResourcesMode resourcesMode() /*default ResourcesMode.One*/;
    
}
