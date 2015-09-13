package br.jus.stf.workflow.domain.model;

import br.jus.stf.shared.domain.model.ProcessoWorkflow;
import br.jus.stf.shared.domain.model.ProcessoWorkflowId;


/**
 * @author Lucas.Rodrigues
 *
 */
public interface ProcessoWorkflowRepository {
	
	public ProcessoWorkflow save(ProcessoWorkflow processoWorkflow);

	public void updateStatus(ProcessoWorkflowId processoWorkflowId, String status);
}
