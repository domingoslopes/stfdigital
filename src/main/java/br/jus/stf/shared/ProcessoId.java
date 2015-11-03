package br.jus.stf.shared;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.stereotype.ValueObject;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:33:25
 */
@Embeddable
public class ProcessoId implements ValueObject<ProcessoId> {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "SEQ_PROCESSO", nullable = false)
	private Long sequencial;

	ProcessoId() {

	}
	
	public ProcessoId(final Long sequencial){
		Validate.notNull(sequencial, "processoId.sequencial.required");
		
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
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sequencial == null) ? 0 : sequencial.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		
		ProcessoId other = (ProcessoId) obj;
		return sameValueAs(other);
	}
	
	@Override
	public boolean sameValueAs(final ProcessoId other){
		return other != null && this.sequencial.equals(other.sequencial);
	}

}