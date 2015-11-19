package br.jus.stf.plataforma.workflow.interfaces.commands;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 26.06.2015
 */
public class SinalizarCommand {

	@NotBlank
	private String sinal;
	
	@NotBlank
	private String status;
	
	private String descricao;

	public void setSinal(String sinal) {
		this.sinal = sinal;
	}

	public String getSinal() {
		return sinal;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
