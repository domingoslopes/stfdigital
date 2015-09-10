package br.jus.stf.workflow.domain.model;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import br.jus.stf.shared.domain.model.ProcessoWorkflow;
import br.jus.stf.shared.domain.model.ProcessoWorkflowId;


/**
 * @author Lucas.Rodrigues
 *
 */
public interface ProcessoWorkflowRepository extends Repository<ProcessoWorkflow, ProcessoWorkflowId> {
	
	public ProcessoWorkflow save(ProcessoWorkflow processoWorkflow);
	
	@Modifying
	@Query("UPDATE ProcessoWorkflow pw SET pw.status = ?2 WHERE pw.id.id = ?1")
	public void updateStatus(String processInstanceId, String status);
}
