package br.jus.stf.plataforma.workflow.application;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.jus.stf.plataforma.workflow.domain.model.ProcessoWokflowRepository;
import br.jus.stf.shared.ProcessoWorkflow;
import br.jus.stf.shared.ProcessoWorkflowId;

/**
 * @author Rodrigo Barreiros
 * @author anderson.araujo
 * 
 * @since 1.0.0
 * @since 26.06.2015
 */
@Service
@Transactional
public class WorkflowApplicationService {

	@Autowired
	private ProcessoWokflowRepository processoWorkflowRepository;

	/**
	 * Inicia uma nova instância do processo
	 * 
	 * @param informacao o ID da informação relacionada ao processo de trabalho
	 * @param mensagem Mensagem que inicia um processo
	 * @param status Status inicial do processo
	 * @return Identificador da instância do processo.
	 */
	public ProcessoWorkflowId iniciar(Long informacao, String mensagem, String status) {
		return processoWorkflowRepository.criar(informacao, mensagem, status);
	}
	
	/**
	 * Emite um sinal para um processo e atualiza seu status
	 * 
	 * @param id
	 * @param sinal
	 * @param status
	 */
	public void sinalizar(ProcessoWorkflow processo, String sinal, String status){
		processoWorkflowRepository.sinalizar(sinal, status);
		processoWorkflowRepository.updateStatus(processo.id(), status);
	}
	
}
