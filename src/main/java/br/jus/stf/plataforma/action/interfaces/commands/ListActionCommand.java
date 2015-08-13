package br.jus.stf.plataforma.action.interfaces.commands;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author Lucas.Rodrigues
 *
 */
public class ListActionCommand {

	@ApiModelProperty(value = "Contexto da informação que sofrerá a ação.", required=true)
	private String context;
	
	@NotBlank
	@ApiModelProperty(value = "Tipo da informação que sofrerá a ação.", required=true)
	private String resourcesType;
	
	@NotNull
	@ApiModelProperty(value = "Recursos que sofreram a ação.", required=true)
	private Collection<?> resources;

	/**
	 * @return the context
	 */
	public String getContext() {
		return context;
	}

	/**
	 * @param context the context to set
	 */
	public void setContext(String context) {
		this.context = context;
	}

	/**
	 * @return the resourceClass
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
	 * @return the resources
	 */
	public Collection<?> getResources() {
		return resources;
	}

	/**
	 * @param resources the resources to set
	 */
	public void setResources(Collection<?> resources) {
		this.resources = resources;
	}
		
}
