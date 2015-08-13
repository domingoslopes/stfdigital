/**
 * 
 */
package br.jus.stf.plataforma.infra.action.support;

import java.lang.annotation.Annotation;

import br.jus.stf.plataforma.infra.action.handlers.ActionConditionHandler;

/**
 * Classe que armazena as informações de um handler da ação.
 * 
 * @author Lucas.Rodrigues
 *
 */
public class ActionConditionHandlerInfo {
	
	private Annotation annotation;
	private ActionConditionHandler<?> handler;
	
	public ActionConditionHandlerInfo(Annotation annotation, ActionConditionHandler<?> handler) {
		this.annotation = annotation;
		this.handler = handler;
	}

	/**
	 * @return the annotation
	 */
	public Annotation getAnnotation() {
		return annotation;
	}
	
	/**
	 * @return the handler
	 */
	public ActionConditionHandler<?> getHandler() {
		return handler;
	}
	
	/**
	 * @param annotation the annotation to set
	 */
	public void setAnnotation(Annotation annotation) {
		this.annotation = annotation;
	}
	
	/**
	 * @param handler the handler to set
	 */
	public void setHandler(ActionConditionHandler<?> handler) {
		this.handler = handler;
	}

}
