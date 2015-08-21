package br.jus.stf.plataforma.domain.model;

import br.jus.stf.shared.domain.model.ProcessInstanceId;
import br.jus.stf.shared.domain.stereotype.Entity;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:34:14
 */
public class ProcessInstance implements Entity<ProcessInstance> {

	private ProcessInstanceId id;
	private ProcessInstanceStatus status;
	
	ProcessInstance() {
		
	}

	public ProcessInstance(final ProcessInstanceId id, final ProcessInstanceStatus status){
		this.id = id;
		this.status = status;
	}

	public ProcessInstanceId id(){
		return this.id;
	}

	public ProcessInstanceStatus status(){
		return this.status;
	}

	/**
	 * 
	 * @param other
	 */
	public boolean sameIdentityAs(final ProcessInstance other){
		return other != null && this.id.sameValueAs(other.id);
	}

}