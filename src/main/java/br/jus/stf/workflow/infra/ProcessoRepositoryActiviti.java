package br.jus.stf.workflow.infra;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.jus.stf.shared.domain.model.ProcessoWorkflow;
import br.jus.stf.shared.domain.model.ProcessoWorkflowId;
import br.jus.stf.workflow.domain.model.ProcessoRepository;

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
	
	@PersistenceContext
	private EntityManager entityManager;
	
	/**
	 * Cria uma instância do processo de autuação de originários.
	 * @param mensagem Nome da mensagem que iniciará o evento de criação do processo.
	 * @return Identificador da instância do processo de autuação criado.
	 */
	@Override
	public String criar(String mensagem) {		
		ProcessInstance processInstance = runtimeService.startProcessInstanceByMessage(mensagem);
		String status = (String) processInstance.getProcessVariables().get("status");
		
		ProcessoWorkflowId id = new ProcessoWorkflowId(processInstance.getId());
		ProcessoWorkflow processoWorkflow = new ProcessoWorkflow(id, status);
		entityManager.persist(processoWorkflow);
		
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
