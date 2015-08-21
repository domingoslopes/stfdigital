package br.jus.stf.plataforma.component.action.resources.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Lucas.Rodrigues
 *
 */
public class ActionDTO {

	private String id;
	private String description;
	private String resourcesType;
	private Boolean hasConditionHandlers = false;
	private String resourcesMode;
	private List<String> neededAuthorithies = new ArrayList<String>(0);

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
	 * @return the hasConditionHandlers
	 */
	public Boolean getHasConditionHandlers() {
		return hasConditionHandlers;
	}

	/**
	 * @param hasConditionHandlers the hasConditionHandlers to set
	 */
	public void setHasConditionHandlers(Boolean hasConditionHandlers) {
		this.hasConditionHandlers = hasConditionHandlers;
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
	 * @return the neededAuthorithies
	 */
	public Collection<String> getNeededAuthorithies() {
		return neededAuthorithies;
	}
	
}
