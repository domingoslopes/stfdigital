package br.jus.stf.plataforma.workflow.domain.model;

import br.jus.stf.shared.ProcessoWorkflow;
import br.jus.stf.shared.ProcessoWorkflowId;



/**
 * @author Rodrigo Barreiros
 * @author anderson.araujo
 * 
 * @since 1.0.0
 * @since 26.06.2015
 */
public interface ProcessoWokflowRepository {
	
	/**
	 * Cria uma instância do processo.
	 * @param mensagem mensagem que iniciará o processo.
	 * @param status status inicial do processo
	 * @return Identificador da instância do processo de autuação criado.
	 */
	ProcessoWorkflowId criar(Long informacao, String mensagem, String status);
	
	/**
	 * Emite um sinal para um processo
	 * 
	 * @param sinal
	 * @param status
	 */
	void sinalizar(String sinal, String status);

	/**
	 * Consulta um processo pelo id
	 * 
	 * @param id
	 * @return
	 */
	ProcessoWorkflow findOne(ProcessoWorkflowId id);
	
	/**
	 * Salva um processo workflow
	 * 
	 * @param processo
	 * @return
	 */
	ProcessoWorkflow save(ProcessoWorkflow processo);

	/**
	 * Atualiza o status do processo
	 * 
	 * @param processoWorkflowId
	 * @param status
	 */
	void updateStatus(ProcessoWorkflowId processoWorkflowId, String status);

	ProcessoWorkflowId criarPorMensagem(Long informacao, String mensagem, String status);
	
}
