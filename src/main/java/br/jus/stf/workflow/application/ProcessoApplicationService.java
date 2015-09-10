package br.jus.stf.workflow.application;

import javax.transaction.Transactional;

import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.jus.stf.shared.domain.model.ProcessoWorkflow;
import br.jus.stf.shared.domain.model.ProcessoWorkflowId;
import br.jus.stf.workflow.domain.model.ProcessoRepository;
import br.jus.stf.workflow.domain.model.ProcessoWorkflowRepository;

/**
 * @author Rodrigo Barreiros
 * @author anderson.araujo
 * 
 * @since 1.0.0
 * @since 26.06.2015
 */
@Service
@Transactional
public class ProcessoApplicationService {

	@Autowired
	private ProcessoRepository processoRepository;
	
	@Autowired
	private ProcessoWorkflowRepository processoWorkflowRepository;

	/**
	 * Inicia uma nova instância do processo de autuação de originários.
	 * @param mensagem Identificador da forma de ingresso de uma petição (física ou eletrônica).
	 * @return Identificador da instãncia do processo de autuação criado.
	 */
	public String iniciar(String mensagem) {
		ProcessInstance processInstance = processoRepository.criar(mensagem);
		String status = (String) processInstance.getProcessVariables().get("status");
		
		ProcessoWorkflowId id = new ProcessoWorkflowId(processInstance.getId());
		ProcessoWorkflow processoWorkflow = new ProcessoWorkflow(id, status);
		processoWorkflowRepository.save(processoWorkflow);
		
		return processInstance.getId();
	}
	
	public ProcessInstance consultar(String id){
		return this.processoRepository.consultar(id);
	}
}
