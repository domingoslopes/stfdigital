package br.jus.stf.workflow.interfaces.dto;

import org.activiti.engine.task.Task;
import org.springframework.stereotype.Component;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 23.06.2015
 */
@Component
public class TarefaDtoAssembler {

	public TarefaDto toDto(Task task) {
		return new TarefaDto(task.getId(), task.getTaskDefinitionKey(), task.getName(), task.getProcessInstanceId());
	}

}
