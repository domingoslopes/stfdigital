package br.jus.stf.plataforma.workflow.domain.model;

import org.activiti.engine.task.Task;
import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.ProcessoWorkflowId;
import br.jus.stf.shared.TarefaId;
import br.jus.stf.shared.stereotype.Entity;

/**
 * @author Lucas.Rodrigues
 *
 */
public class Tarefa implements Entity<Tarefa, TarefaId> {
	
	private TarefaId id;
	private Task task;
	private ProcessoWorkflowId processo;
	
	public Tarefa(Task task) {
		Validate.notNull(task, "tarefa.task.required");
		
		Long id = Long.parseLong(task.getId());	
		this.id = new TarefaId(id);
		this.task = task;
		Long processo = Long.parseLong(task.getProcessInstanceId());
		this.processo = new ProcessoWorkflowId(processo);
	}
	
	@Override
	public TarefaId id() {
		return id;
	}
	
	/**
	 * Definição de uma tarefa
	 * 
	 * @return tarefa do activiti
	 */
	public Task definicao() {
		return task;
	}
	
	public ProcessoWorkflowId processo() {
		return processo;
	}

	@Override
	public boolean sameIdentityAs(Tarefa other) {
		return other != null && id.equals(other.id);
	}
	
}
