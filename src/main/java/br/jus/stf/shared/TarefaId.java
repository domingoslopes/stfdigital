package br.jus.stf.shared;

import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.stereotype.ValueObject;

/**
 * @author Lucas.Rodrigues
 *
 */
public class TarefaId implements ValueObject<TarefaId> {

	private static final long serialVersionUID = 747808680221902331L;
	
	private Long sequencial;
	
	public TarefaId(final Long sequencial) {
		Validate.notNull(sequencial, "tarefaid.sequencial.required");
		
		this.sequencial = sequencial;
	}

	public Long toLong() {
		return sequencial;
	}
	
	@Override
	public String toString() {
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
		
		TarefaId other = (TarefaId) obj;
		return sameValueAs(other);
	}

	@Override
	public boolean sameValueAs(TarefaId other) {
		return other != null && sequencial.equals(other.sequencial);
	}
	
}
