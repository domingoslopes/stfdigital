package br.jus.stf.plataforma.workflow.interfaces.commands;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 26.06.2015
 */
public class IniciarProcessoCommand {

	private String idProcesso;

	public void setIdProcesso(String idProcesso) {
		this.idProcesso = idProcesso;
	}

	public String getIdProcesso() {
		return idProcesso;
	}

}
