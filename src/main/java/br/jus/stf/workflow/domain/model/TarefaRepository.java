package br.jus.stf.workflow.domain.model;

import java.util.List;

import org.activiti.engine.task.Task;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 26.06.2015
 */
public interface TarefaRepository {

	void completar(String taskId);

	List<Task> listar(String papel);
	
	void sinalizar(String sinal, String executionId);
	
	Task consultarPorProcesso(String idProcesso);
	
	Task consultar(String taskId);
}
