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
	 * @param chave mensagem que iniciará o processo.
	 * @param metadado metadado do objeto alvo do processo. 
	 * @return Identificador da instância do processo de autuação criado.
	 */
	ProcessoWorkflowId criar(String chave, Metadado metadado);
	
	/**
	 * Inicia um processo por uma mensagem
	 * @param mensagem mensagem que iniciará o processo.
	 * @param metadado metadado do objeto alvo do processo. 
	 * @return Identificador da instância do processo de autuação criado.
	 */
	ProcessoWorkflowId criarPorMensagem(String mensagem, Metadado metadado);
	
	/**
	 * Emite um sinal para um processo
	 * 
	 * @param sinal
	 * @param metadado
	 */
	void sinalizar(String sinal, Metadado metadado);

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
	<T extends ProcessoWorkflow> T save(ProcessoWorkflow processo);

	/**
	 * Atualiza o status do processo
	 * 
	 * @param processoWorkflowId
	 * @param status
	 */
	void updateStatus(ProcessoWorkflowId processoWorkflowId, String status);
	
}
