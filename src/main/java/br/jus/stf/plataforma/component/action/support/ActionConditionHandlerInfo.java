/**
 * 
 */
package br.jus.stf.plataforma.component.action.support;

import java.lang.annotation.Annotation;

/**
 * Classe que armazena as informações de um handler da ação.
 * 
 * @author Lucas.Rodrigues
 *
 */
public class ActionConditionHandlerInfo {
	
	private Annotation annotation;
	private Class<?> handlerClass;
	
	public ActionConditionHandlerInfo(Annotation annotation, Class<?> handlerClass) {
		this.annotation = annotation;
		this.handlerClass = handlerClass;
	}

	/**
	 * @return the annotation
	 */
	public Annotation getAnnotation() {
		return annotation;
	}
	
	/**
	 * @return the handlerClass
	 */
	public Class<?> getHandlerClass() {
		return handlerClass;
	}

}
