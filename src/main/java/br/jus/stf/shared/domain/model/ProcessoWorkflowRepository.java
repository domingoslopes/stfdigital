package br.jus.stf.shared.domain.model;

import java.util.List;

import org.springframework.data.repository.Repository;


/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:34:14
 */
public interface ProcessoWorkflowRepository extends Repository<ProcessoWorkflow, ProcessoWorkflowId> {

	/**
	 * 
	 * @param id
	 */
	public ProcessoWorkflow findOne(ProcessoWorkflowId id);
	
	/**
	 * @param status
	 * @return
	 */
	public List<ProcessoWorkflow> findByStatus(String status);

}