package br.jus.stf.plataforma.workflow.infra;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import br.jus.stf.plataforma.workflow.domain.ProcessoRepository;

/**
 * @author Rodrigo Barreiros
 * @author anderson.araujo
 * 
 * @since 1.0.0
 * @since 26.06.2015
 */
@Repository
public class ProcessoRepositoryActiviti implements ProcessoRepository {

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;
	
	/**
	 * Cria uma instância do processo de autuação de originários.
	 * @param mensagem Nome da mensagem que iniciará o evento de criação do processo.
	 * @return Identificador da instância do processo de autuação criado.
	 */
	@Override
	public String criar(String mensagem) {
		ProcessInstance processInstance = runtimeService.startProcessInstanceByMessage(mensagem);
		return processInstance.getId();
	}
	
	public void alterar(String id, String nome, String valor){
		Task task = taskService.createTaskQuery().taskId(id).singleResult();
		if (task != null) {
			id = task.getProcessInstanceId();
		}
		this.runtimeService.setVariable(id, nome, valor);
	}
	
	public ProcessInstance consultar(String id){
    	String processInstanceId = taskService.createTaskQuery().taskId(id).singleResult().getProcessInstanceId();
    	
    	return runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).includeProcessVariables().singleResult();
	}
}
