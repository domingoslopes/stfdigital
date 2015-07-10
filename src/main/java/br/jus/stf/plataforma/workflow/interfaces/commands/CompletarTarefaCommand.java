package br.jus.stf.plataforma.workflow.interfaces.commands;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 26.06.2015
 */
public class CompletarTarefaCommand {

	private String idTarefa;

	public void setIdTarefa(String idTarefa) {
		this.idTarefa = idTarefa;
	}

	public String getIdTarefa() {
		return idTarefa;
	}

}
