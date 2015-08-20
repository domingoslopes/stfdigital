package br.jus.stf.plataforma.domain.model;

import br.jus.stf.shared.domain.model.ProcessInstanceId;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:34:14
 */
public interface ProcessInstanceRepository {

	/**
	 * 
	 * @param id
	 */
	public ProcessInstance find(ProcessInstanceId id);

	/**
	 * 
	 * @param processInstance
	 */
	public void store(ProcessInstance processInstance);

}