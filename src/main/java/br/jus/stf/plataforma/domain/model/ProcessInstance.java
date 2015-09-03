package br.jus.stf.plataforma.domain.model;

import org.apache.commons.lang3.Validate;

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
	
	public ProcessInstance(final ProcessInstanceId id, final ProcessInstanceStatus status){
		Validate.notNull(id, "processInstance.id.required");
		Validate.notNull(status, "processInstance.status.required");
		
		this.id = id;
		this.status = status;
	}

	public ProcessInstanceId id(){
		return this.id;
	}

	public ProcessInstanceStatus status(){
		return this.status;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || !(obj instanceof ProcessInstance)) return false;
	    
	    final ProcessInstance other = (ProcessInstance) obj;
	    return sameIdentityAs(other);
	}

	/**
	 * 
	 * @param other
	 */
	public boolean sameIdentityAs(final ProcessInstance other){
		return other != null && this.id.sameValueAs(other.id);
	}
	
	//Hibernate
	
	ProcessInstance() {
		
	}

}