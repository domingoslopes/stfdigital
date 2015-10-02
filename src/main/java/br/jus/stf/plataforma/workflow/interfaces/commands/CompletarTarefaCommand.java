package br.jus.stf.plataforma.workflow.interfaces.commands;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Lucas.Rodrigues
 *
 */
public class CompletarTarefaCommand {
	
	@NotBlank
	private String status;
	
	/**
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return this.status;
	}
	
}
