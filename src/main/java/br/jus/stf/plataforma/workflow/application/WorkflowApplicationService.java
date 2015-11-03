package br.jus.stf.plataforma.workflow.application;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.jus.stf.plataforma.workflow.domain.model.Metadado;
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
	 * @param chave Chave que inicia um processo
	 * @param metadado Metadado do objeto alvo do processo
	 * @return Identificador da instância do processo.
	 */
	public ProcessoWorkflowId iniciar(String chave, Metadado metadado) {
		return processoWorkflowRepository.criar(chave, metadado);
	}
	
	/**
	 * Inicia uma nova instância do processo por mensagem
	 * 
	 * @param mensagem
	 * @param metadado
	 * @return
	 */
	public ProcessoWorkflowId iniciarPorMensagem(String mensagem, Metadado metadado) {
		return processoWorkflowRepository.criarPorMensagem(mensagem, metadado);
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
