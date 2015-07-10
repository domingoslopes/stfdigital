package br.jus.stf.plataforma.workflow.interfaces.commands;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 26.06.2015
 */
public class SinalizarCommand {

	private String sinal;

	public void setSinal(String sinal) {
		this.sinal = sinal;
	}

	public String getSinal() {
		return sinal;
	}

}
