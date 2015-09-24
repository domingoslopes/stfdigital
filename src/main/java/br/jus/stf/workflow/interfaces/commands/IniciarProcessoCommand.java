package br.jus.stf.workflow.interfaces.commands;

import javax.validation.constraints.NotNull;

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
	
	@NotNull
	private Long informacao;
	
	@NotBlank
	private String status;
		
	public IniciarProcessoCommand(Long informacao, String mensagem) {
		this.informacao = informacao;
		this.mensagem = mensagem;
	}
	
	public IniciarProcessoCommand() {
	}

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
	
	public void setInformacao(Long informacao) {
		this.informacao = informacao;
	}
	
	public Long getInformacao() {
		return informacao;
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
