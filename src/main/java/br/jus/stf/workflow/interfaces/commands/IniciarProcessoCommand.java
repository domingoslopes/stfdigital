package br.jus.stf.workflow.interfaces.commands;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Rodrigo Barreiros
 * @author anderson.araujo
 * 
 * @since 1.0.0
 * @since 26.06.2015
 */
public class IniciarProcessoCommand {

	@NotBlank
	private String mensagem;
	
	@NotBlank
	private String status;
		
	/**
	 * @param mensagem
	 */
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	/**
	 * @return the mensagem
	 */
	public String getMensagem() {
		return this.mensagem;
	}
	
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
