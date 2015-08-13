package br.jus.stf.plataforma.component.action.support;

import java.util.ArrayList;
import java.util.List;

import br.jus.stf.plataforma.component.action.annotation.ActionMapping;
import br.jus.stf.plataforma.component.action.annotation.ActionMapping.ResourcesMode;

/**
 * Armazena as metainformações definidas na anotação {@link ActionMapping}.
 * 
 * @author Lucas.Rodrigues
 * 
 */
public class ActionMappingInfo {

	private String id;
	private String description;
	private ActionControllerInfo controllerInfo;
	private String methodName;
	private Class<?> resourceClass;
	private ResourcesMode resourcesMode;
	private List<String> grantedAuthorities = new ArrayList<String>();
	private List<ActionConditionHandlerInfo> actionHandlersInfo = new ArrayList<ActionConditionHandlerInfo>(0);
	
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
	 * @param controllerInfo the controllerInfo to set
	 */
	public void setControllerInfo(ActionControllerInfo controllerInfo) {
		this.controllerInfo = controllerInfo;
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
	 * @return the resourceClass
	 */
	public Class<?> getResourceClass() {
		return resourceClass;
	}

	/**
	 * @param resourceClass the resourceClass to set
	 */
	public void setResourceClass(Class<?> resourceClass) {
		this.resourceClass = resourceClass;
	}
	
	/**
	 * @return the resourcesType
	 */
	public String getResourcesType() {
		return resourceClass.getSimpleName();
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
	 * @return the context of controller
	 */
	public String getContext() {
		return controllerInfo.getContext();
	}

	/**
	 * @return the controller bean
	 */
	public Object getController() {
		return controllerInfo.getController();
	}

	/**
	 * @return the grantedAuthorities
	 */
	public List<String> getGrantedAuthorities() {
		return grantedAuthorities;
	}

	/**
	 * @return the actionHandlersInfo
	 */
	public List<ActionConditionHandlerInfo> getActionHandlersInfo() {
		return actionHandlersInfo;
	}

}
