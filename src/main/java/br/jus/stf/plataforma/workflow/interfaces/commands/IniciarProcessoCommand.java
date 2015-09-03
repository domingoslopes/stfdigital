package br.jus.stf.plataforma.workflow.interfaces.commands;

/**
 * @author Rodrigo Barreiros
 * @author anderson.araujo
 * 
 * @since 1.0.0
 * @since 26.06.2015
 */
public class IniciarProcessoCommand {

	private String tipoRecebimento;
		
	public void setTipoRecebimento(String tipoRecebimento) {
		this.tipoRecebimento = tipoRecebimento;
	}

	public String getTipoRecebimento() {
		return this.tipoRecebimento;
	}
}
