package br.jus.stf.plataforma.domain.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.domain.model.ProcessInstanceId;
import br.jus.stf.shared.domain.stereotype.Entity;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:34:14
 */
@javax.persistence.Entity
@Table(name = "PROCESS_INSTANCE", schema = "AUTUACAO")
public class ProcessInstance implements Entity<ProcessInstance> {

	@EmbeddedId
	private ProcessInstanceId id;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "TIP_STATUS_INSTANCE")
	private ProcessInstanceStatus status;
	
	ProcessInstance() {
		
	}

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
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		
		ProcessInstance other = (ProcessInstance) obj;
		return sameIdentityAs(other);
	}

	@Override
	public boolean sameIdentityAs(final ProcessInstance other){
		return other != null && this.id.sameValueAs(other.id);
	}

}