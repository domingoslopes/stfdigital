package br.jus.stf.workflow.interfaces.dto;

import org.activiti.engine.task.Task;
import org.springframework.stereotype.Component;

import br.jus.stf.workflow.domain.model.Tarefa;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 23.06.2015
 */
@Component
public class TarefaDtoAssembler {

	public TarefaDto toDto(Tarefa tarefa) {
		Task task = tarefa.definicao();
		Long id = tarefa.id().toLong();
		Long processo = Long.parseLong(task.getProcessInstanceId());
		Long idInformacao = (Long) task.getProcessVariables().get("informacao");
		return new TarefaDto(id, task.getTaskDefinitionKey(), task.getName(), processo, idInformacao);
	}

}
