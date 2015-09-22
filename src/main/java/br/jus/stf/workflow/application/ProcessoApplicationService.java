package br.jus.stf.workflow.application;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.jus.stf.shared.domain.model.ProcessoWorkflow;
import br.jus.stf.shared.domain.model.ProcessoWorkflowId;
import br.jus.stf.workflow.domain.model.ProcessoWokflowRepository;

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
	private ProcessoWokflowRepository processoWorkflowRepository;

	/**
	 * Inicia uma nova instância do processo
	 * @param mensagem Mensagem que inicia um processo
	 * @param status Status inicial do processo
	 * @return Identificador da instância do processo.
	 */
	public ProcessoWorkflowId iniciar(String mensagem, String status) {
		return processoWorkflowRepository.criar(mensagem, status);
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
