package br.jus.stf.plataforma.workflow.interfaces.commands;

/**
 * @author Lucas.Rodrigues
 *
 */
public class DelegarTarefaCommand {

	private Long tarefaId;
	private Long usuarioId;
	
	/**
	 * @return the tarefaId
	 */
	public Long getTarefaId() {
		return tarefaId;
	}
	
	/**
	 * @param tarefaId the tarefaId to set
	 */
	public void setTarefaId(Long tarefaId) {
		this.tarefaId = tarefaId;
	}

	/**
	 * @return the usuarioId
	 */
	public Long getUsuarioId() {
		return usuarioId;
	}

	/**
	 * @param usuarioId the usuarioId to set
	 */
	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}
	
}
