/**
 * 
 */
package br.jus.stf.plataforma.action.interfaces.commands;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author Lucas.Rodrigues
 *
 */
public class ExecuteActionCommand {

	@NotBlank
	@ApiModelProperty(value = "Identificador da ação.", required=true)
	private String actionId;
	
	@NotNull
	@ApiModelProperty(value = "Recursos que sofreram a ação.", required=true)
	private ArrayNode resources;

	/**
	 * @return the actionId
	 */
	public String getActionId() {
		return actionId;
	}
	
	/**
	 * @param actionId the actionId to set
	 */
	public void setActionId(String actionId) {
		this.actionId = actionId;
	}

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
