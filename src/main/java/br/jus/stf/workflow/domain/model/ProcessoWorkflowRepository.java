package br.jus.stf.workflow.domain.model;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.jus.stf.shared.domain.model.ProcessoWorkflow;


/**
 * @author Lucas.Rodrigues
 *
 */
public interface ProcessoWorkflowRepository extends br.jus.stf.shared.domain.model.ProcessoWorkflowRepository {
	
	public ProcessoWorkflow save(ProcessoWorkflow processoWorkflow);
	
	@Modifying
	@Query("UPDATE ProcessoWorkflow pw SET pw.status = 2? WHERE pw.processoWorkflowId.id = 1?")
	public void updateStatus(String processInstanceId, String status);
}
