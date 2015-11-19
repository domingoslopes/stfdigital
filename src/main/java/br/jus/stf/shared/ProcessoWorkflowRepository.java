package br.jus.stf.shared;

import java.util.List;

import org.springframework.data.repository.Repository;


/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:34:14
 */
public interface ProcessoWorkflowRepository extends Repository<ProcessoWorkflow, ProcessoWorkflowId> {

	/**
	 * Pesquisa um processo de workflow pelo id
	 * 
	 * @param id
	 * @return processo
	 */
	public ProcessoWorkflow findOne(ProcessoWorkflowId id);
	
	/**
	 * Consulta processos pelo status
	 * 
	 * @param status
	 * @return uma lista de processos
	 */
	public List<ProcessoWorkflow> findByStatus(String status);

}