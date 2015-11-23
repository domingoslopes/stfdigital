package br.jus.stf.shared;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.jus.stf.shared.stereotype.Entity;

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
	
	ProcessoWorkflow() {

	}
	
	public ProcessoWorkflow(final ProcessoWorkflowId id, final String status){
		Validate.notNull(id, "processoWorkflow.id.required");
		Validate.notBlank(status, "processoWorkflow.status.required");
		
		this.id = id;
		this.status = status;
	}

	@Override
	public ProcessoWorkflowId id() {
		return id;
	}

	public String status() {
		return status;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		
		ProcessoWorkflow other = (ProcessoWorkflow) obj;
		return sameIdentityAs(other);
	}

	@Override
	public boolean sameIdentityAs(final ProcessoWorkflow other){
		return other != null && this.id.sameValueAs(other.id);
	}

}
