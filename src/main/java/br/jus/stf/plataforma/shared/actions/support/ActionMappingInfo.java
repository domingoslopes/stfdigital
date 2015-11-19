package br.jus.stf.plataforma.shared.actions.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.jus.stf.plataforma.shared.actions.annotation.ActionMapping;
import br.jus.stf.plataforma.shared.security.SecurityContextUtil;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonView;

/**
 * Armazena as metainformações definidas na anotação {@link ActionMapping}.
 * 
 * @author Lucas.Rodrigues
 * 
 */
public class ActionMappingInfo {

	@JsonView(ActionView.class)
	private String id;
	
	@JsonView(ActionView.class)
	private String description;
	
	private Class<?> controllerClass;
	private String methodName;
	private Class<?> resourcesClass;
	
	@JsonView(ActionView.class)
	private ResourcesMode resourcesMode;
	
	@JsonView(ActionView.class)
	private List<String> neededAuthorities = new ArrayList<String>();
	
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
	 * @param resourcesClass the resourcesClass to set
	 */
	public void setResourcesClass(Class<?> resourcesClass) {
		this.resourcesClass = resourcesClass;
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
	 * @return the neededAuthorities
	 */
	public List<String> getNeededAuthorities() {
		return neededAuthorities;
	}

	/**
	 * @return the actionHandlersInfo
	 */
	public List<ActionConditionHandlerInfo> getActionHandlersInfo() {
		return actionHandlersInfo;
	}
	
	@JsonView(ActionView.class)
	@JsonGetter("resourcesType")
	public String getResourcesType() {
		if (resourcesClass == null) {
			return "";
		}
		return resourcesClass.getSimpleName();
	}
	
	@JsonView(ActionView.class)
	@JsonGetter("hasConditionHandlers")
	public boolean hasConditionHandlers() {
		return !actionHandlersInfo.isEmpty();
	}
		
	/**
	 * Verifica se o resourceMode é o indicado para a quantidade de recursos
	 * 
	 * @param resources
	 * @return
	 */
	public boolean isValidResourceMode(Collection<?> resources) {
		if (resources == null) {
			return resourcesMode.equals(ResourcesMode.None);
		}
		
		int size = resources.size();
		
		if (size == 0) {
			return resourcesMode.equals(ResourcesMode.None);
		} else if (size == 1) {
			return resourcesMode.equals(ResourcesMode.One) || resourcesMode.equals(ResourcesMode.Many);
		}
		return resourcesMode.equals(ResourcesMode.Many);
	}
	
	/**
	 * Verifica se o usuário possui os papéis necessários
	 * 
	 * @return
	 */
	public boolean hasNeededAuthorities() {
		if (neededAuthorities.isEmpty()) {
			return true;
		}
		List<String> autorizacoes = SecurityContextUtil.getAutorizacoes();
		return autorizacoes.containsAll(neededAuthorities);
	}

}
