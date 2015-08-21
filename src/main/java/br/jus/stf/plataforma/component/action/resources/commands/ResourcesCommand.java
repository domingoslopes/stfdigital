package br.jus.stf.plataforma.component.action.resources.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author Lucas.Rodrigues
 *
 */
public class ResourcesCommand {

	@ApiModelProperty(value = "Recursos que sofrerão a ação.", required=true)
	private ArrayNode resources;
	
	/**
	 * @return the resources
	 */
	public ArrayNode getResources() {
		return resources;
	}

	/**
	 * @param resources the resources to set
	 */
	public void setResources(ArrayNode resources) {
		this.resources = resources;
	}
	
}
