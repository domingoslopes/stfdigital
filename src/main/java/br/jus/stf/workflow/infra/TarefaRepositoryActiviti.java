package br.jus.stf.workflow.infra;

import java.util.List;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.jus.stf.workflow.domain.model.TarefaRepository;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 26.06.2015
 */
@Repository
public class TarefaRepositoryActiviti implements TarefaRepository {

	@Autowired
	private TaskService taskService;

	@Autowired
	private RuntimeService runtimeService;

	@Override
	public List<Task> listar(String papel) {
		return taskService.createTaskQuery().taskCandidateGroup(papel).list(); 
	}

	@Override
	public void completar(String taskId) {
		taskService.complete(taskId);
	}
	
	@Override
	public void sinalizar(String sinal, String executionId) {
		runtimeService.signalEventReceived(sinal, executionId);
	}

	@Override
	public Task consultar(String taskId) {
		return taskService.createTaskQuery().taskId(taskId).singleResult();
	}
}
