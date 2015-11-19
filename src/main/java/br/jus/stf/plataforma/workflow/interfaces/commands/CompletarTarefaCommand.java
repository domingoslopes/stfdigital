package br.jus.stf.plataforma.workflow.interfaces.commands;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Lucas.Rodrigues
 *
 */
public class CompletarTarefaCommand {
	
	@NotBlank
	private String status;
	
	private String descricao;
	
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

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
