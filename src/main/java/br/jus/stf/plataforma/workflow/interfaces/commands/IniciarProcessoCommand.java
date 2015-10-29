package br.jus.stf.plataforma.workflow.interfaces.commands;

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
	
	@NotNull
	private String tipoInformacao;
	
	@NotBlank
	private String status;
		
	public IniciarProcessoCommand(Long informacao, String tipoInformacao, String mensagem) {
		this.informacao = informacao;
		this.tipoInformacao = tipoInformacao;
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
	
	public void setTipoInformacao(String tipoInformacao) {
		this.tipoInformacao = tipoInformacao;
	}
	
	public String getTipoInformacao() {
		return tipoInformacao;
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
