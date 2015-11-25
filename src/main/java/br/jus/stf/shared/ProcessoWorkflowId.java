package br.jus.stf.shared;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.jus.stf.shared.stereotype.ValueObject;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:33:46
 */
@Embeddable
public class ProcessoWorkflowId implements ValueObject<ProcessoWorkflowId> {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "NUM_PROCESS_INSTANCE", nullable = false)
	private Long sequencial;
	
	ProcessoWorkflowId() {

	}
	
	public ProcessoWorkflowId(final Long sequencial) {
		Validate.notNull(sequencial, "processoWorkflowId.sequencial.required");
		this.sequencial = sequencial;
	}
	
	public Long toLong(){
		return sequencial;
	}
	
	@Override
	public String toString(){
		return sequencial.toString();
	}	

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(sequencial).toHashCode();
	}
	
	@Override
	public boolean equals(final Object obj){
		if (this == obj) {
			return true;
		}
		
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
	
		ProcessoWorkflowId other = (ProcessoWorkflowId) obj;
		return sameValueAs(other);
	}

	@Override
	public boolean sameValueAs(final ProcessoWorkflowId other){
		return other != null && this.sequencial.equals(other.sequencial);
	}
	
}
