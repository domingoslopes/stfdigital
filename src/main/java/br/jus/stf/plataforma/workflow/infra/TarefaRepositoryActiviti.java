package br.jus.stf.plataforma.workflow.infra;

import java.util.List;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.jus.stf.plataforma.workflow.domain.TarefaRepository;

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
	
	@Autowired
	private IdentityService identityService;

	@Override
	public List<Task> listar(String papel) {
		List<Task> tarefas = taskService.createTaskQuery().taskCandidateGroup(papel).list(); 
		return tarefas;
	}

	@Override
	public void completar(String id) {
		taskService.complete(id);
	}

	@Override
	public void sinalizar(String sinal) {
		runtimeService.signalEventReceived(sinal);
	}

	@Override
	public Task consultar(String id) {
		return this.taskService.createTaskQuery().taskId(id).singleResult();
	}
}
