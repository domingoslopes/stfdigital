package br.jus.stf.plataforma.infra.action.support;

/**
 * Armazena as informações de um controlador com a anotação
 * {@link br.jus.stf.plataforma.infra.action.annotation.ActionController#member ActionController}
 * 
 * @author Lucas.Rodrigues
 *
 */
public class ActionControllerInfo {

	private Object controller;
	private String context;
	
	public ActionControllerInfo(Object controller, String context) {
		this.controller = controller;
		this.context = context;
	}
	
	/**
	 * @return the controller
	 */
	public Object getController() {
		return controller;
	}
	/**
	 * @return the context
	 */
	public String getContext() {
		return context;
	}
	/**
	 * @param controller the controller to set
	 */
	public void setController(Object controller) {
		this.controller = controller;
	}
	/**
	 * @param context the context to set
	 */
	public void setContext(String context) {
		this.context = context;
	}
	
}
