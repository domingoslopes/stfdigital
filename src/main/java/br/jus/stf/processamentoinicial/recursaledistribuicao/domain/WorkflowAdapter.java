package br.jus.stf.processamentoinicial.recursaledistribuicao.domain;

import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.ProcessoRecursal;
import br.jus.stf.shared.ProcessoWorkflow;

/**
 * @author Lucas.Rodrigues
 * 
 * @since 1.0.0
 * @since 15.03.2016
 */
public interface WorkflowAdapter {

	/**
	 * Iniciar uma nova instância do processo de autuação de processo recursal enviado por tribunal
	 * @param processoRecursal
	 */
	ProcessoWorkflow iniciarWorkflow(ProcessoRecursal processoRecursal);
	
}
