package br.jus.stf.workflow.infra;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
	 * Cria uma instãncia do processo de autuação de originários.
	 * @param mensagem Nome da mensagem que iniciaráo evento de criação do processo.
	 * @return Identificador da instância do processo de autuação criado.
	 */
	@Override
	public ProcessInstance criar(String mensagem) {
		return runtimeService.startProcessInstanceByMessage(mensagem);
	}
	
	@Override
	public ProcessInstance consultar(String processInstanceId){    	
    	return runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).includeProcessVariables().singleResult();
	}
}
