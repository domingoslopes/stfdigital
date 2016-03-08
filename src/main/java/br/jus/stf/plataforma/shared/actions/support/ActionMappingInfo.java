package br.jus.stf.plataforma.shared.actions.support;

import java.util.HashSet;
import java.util.Set;

import br.jus.stf.plataforma.shared.actions.annotation.ActionMapping;
import br.jus.stf.plataforma.shared.security.resource.ResourceImpl;
import br.jus.stf.plataforma.shared.security.resource.ResourceType;

/**
 * Armazena as metainformações definidas na anotação {@link ActionMapping}.
 * 
 * @author Lucas.Rodrigues
 * 
 */
public class ActionMappingInfo extends ResourceImpl {

	private String id;
	private String description;
	private Class<?> controllerClass;
	private Set<String> groupClasses = new HashSet<String>(1);
	private String methodName;
	private Class<?> resourcesClass;
	private ResourcesMode resourcesMode;
	private Set<ActionConditionHandlerInfo> actionHandlersInfo = new HashSet<ActionConditionHandlerInfo>(0);
	
	public ActionMappingInfo(String id) {
		super(id, ResourceType.ACAO);
		this.id = id;
	}
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the controllerClass
	 */
	public Class<?> getControllerClass() {
		return controllerClass;
	}

	/**
	 * @param controllerClass the controllerClass to set
	 */
	public void setControllerClass(Class<?> controllerClass) {
		this.controllerClass = controllerClass;
	}
	
	/**
	 * @return the groupClasses
	 */
	public Set<String> getGroupClasses() {
		return groupClasses;
	}

	/**
	 * @return the method name of action
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * @param methodName the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	/**
	 * @return the resourcesClass
	 */
	public Class<?> getResourcesClass() {
		return resourcesClass;
	}

	/**
	 * @param rersourcesClass the rersourcesClass to set
	 */
	public void setResourcesClass(Class<?> rersourcesClass) {
		this.resourcesClass = rersourcesClass;
	}

	/**
	 * @return the resourcesMode
	 */
	public ResourcesMode getResourcesMode() {
		return resourcesMode;
	}

	/**
	 * @param resourcesMode the resourcesMode to set
	 */
	public void setResourcesMode(ResourcesMode resourcesMode) {
		this.resourcesMode = resourcesMode;
	}

	/**
	 * @return the actionHandlersInfo
	 */
	public Set<ActionConditionHandlerInfo> getActionHandlersInfo() {
		return actionHandlersInfo;
	}

}
