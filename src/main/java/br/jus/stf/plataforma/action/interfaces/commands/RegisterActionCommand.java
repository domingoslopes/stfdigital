package br.jus.stf.plataforma.action.interfaces.commands;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Lucas.Rodrigues
 *
 */
public class RegisterActionCommand {

	@NotBlank
	private String id;
	
	@NotBlank
	private String description;
	
	@NotBlank
	private String context;
	
	@NotBlank
	private String resourcesType;
	
	@NotBlank
	private String resourcesMode;
	
	private Boolean hasConditionHandlers;
	
	private List<String> grantedAuthorities = new ArrayList<String>(0);

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the context
	 */
	public String getContext() {
		return context;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param context the context to set
	 */
	public void setContext(String context) {
		this.context = context;
	}

	/**
	 * @return the resourcesType
	 */
	public String getResourcesType() {
		return resourcesType;
	}

	/**
	 * @param resourcesType the resourcesType to set
	 */
	public void setResourcesType(String resourcesType) {
		this.resourcesType = resourcesType;
	}

	/**
	 * @return the resourcesMode
	 */
	public String getResourcesMode() {
		return resourcesMode;
	}

	/**
	 * @param resourcesMode the resourcesMode to set
	 */
	public void setResourcesMode(String resourcesMode) {
		this.resourcesMode = resourcesMode;
	}

	/**
	 * @return the hasConditionHandlers
	 */
	public boolean hasConditionHandlers() {
		return hasConditionHandlers;
	}

	/**
	 * @param hasConditionHandlers the hasConditionHandlers to set
	 */
	public void setHasConditionHandlers(Boolean hasConditionHandlers) {
		this.hasConditionHandlers = hasConditionHandlers;
	}

	/**
	 * @return the grantedAuthorities
	 */
	public List<String> getGrantedAuthorities() {
		return grantedAuthorities;
	}
	
}
