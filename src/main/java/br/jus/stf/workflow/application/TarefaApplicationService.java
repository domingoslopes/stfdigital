package br.jus.stf.workflow.application;

import java.util.List;

import javax.transaction.Transactional;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.jus.stf.workflow.domain.model.ProcessoRepository;
import br.jus.stf.workflow.domain.model.ProcessoWorkflowRepository;
import br.jus.stf.workflow.domain.model.TarefaRepository;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 26.06.2015
 */
@Service
@Transactional
public class TarefaApplicationService {

	@Autowired
	private TarefaRepository tarefaRepository;
	
	@Autowired
	private ProcessoRepository processoRepository;
	
	@Autowired
	private ProcessoWorkflowRepository processoWorkflowRepository;

	public List<Task> listar(String papel) {
		return tarefaRepository.listar(papel);
	}

	public void completar(String taskId) {
		tarefaRepository.completar(taskId);
		Task task = consultar(taskId);
		ProcessInstance processInstance = processoRepository.consultar(task.getProcessInstanceId());
		String status = (String) processInstance.getProcessVariables().get("status");
		
		processoWorkflowRepository.updateStatus(processInstance.getId(), status);
	}
	
	public void sinalizar(String sinal, String taskId){
		Task task = consultar(taskId);
		this.tarefaRepository.sinalizar(sinal, task.getExecutionId());
	}

	public Task consultar(String taskId){
		return tarefaRepository.consultar(taskId);
	}
}
