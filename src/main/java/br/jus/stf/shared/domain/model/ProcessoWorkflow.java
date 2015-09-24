package br.jus.stf.shared.domain.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.domain.stereotype.Entity;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:34:14
 */
@javax.persistence.Entity
@Table(name = "PROCESSO_WORKFLOW", schema = "PLATAFORMA")
public class ProcessoWorkflow implements Entity<ProcessoWorkflow, ProcessoWorkflowId> {

	@EmbeddedId
	private ProcessoWorkflowId id;
	
	@Column(name = "DSC_STATUS_PROCESS_INSTANCE")
	private String status;
	
	public ProcessoWorkflow(final ProcessoWorkflowId id, final String status){
		Validate.notNull(id, "processoWorkflow.id.required");
		Validate.notBlank(status, "processoWorkflow.status.required");
		
		this.id = id;
		this.status = status;
	}

	public ProcessoWorkflowId id() {
		return id;
	}

	public String status() {
		return status;
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
		
		ProcessoWorkflow other = (ProcessoWorkflow) obj;
		return sameIdentityAs(other);
	}

	@Override
	public boolean sameIdentityAs(final ProcessoWorkflow other){
		return other != null && this.id.sameValueAs(other.id);
	}
	
	//Hibernate
	
	ProcessoWorkflow() {
		
	}

}
