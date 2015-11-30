package br.jus.stf.plataforma.shared.actions.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import br.jus.stf.plataforma.shared.actions.annotation.ActionMapping;
import br.jus.stf.plataforma.shared.security.SecurityContextUtil;

/**
 * Armazena as metainformações definidas na anotação {@link ActionMapping}.
 * 
 * @author Lucas.Rodrigues
 * 
 */
public class ActionMappingInfo {

	private String id;
	private String description;
	private Class<?> controllerClass;
	private String methodName;
	private Class<?> resourcesClass;
	private ResourcesMode resourcesMode;
	private Set<GrantedAuthority> neededAuthorities = new HashSet<GrantedAuthority>();
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
	public Set<GrantedAuthority> getNeededAuthorities() {
		return neededAuthorities;
	}

	/**
	 * @return the actionHandlersInfo
	 */
	public List<ActionConditionHandlerInfo> getActionHandlersInfo() {
		return actionHandlersInfo;
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
		return SecurityContextUtil.userContainsAll(neededAuthorities);
	}

}
