package br.jus.stf.plataforma.workflow.interfaces.dto;

import org.activiti.engine.task.Task;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 23.06.2015
 */
public class TarefaDtoAssembler {

	public TarefaDto toDto(Task task) {
		return new TarefaDto(task.getId(), task.getTaskDefinitionKey(), task.getName(), task.getProcessInstanceId());
	}

}
