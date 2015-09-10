package br.jus.stf.shared.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.domain.stereotype.ValueObject;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:33:25
 */
@Embeddable
public class ProcessoId implements ValueObject<ProcessoId> {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "SEQ_PROCESSO", nullable = false)
	private Long id;

	public ProcessoId(final Long id){
		Validate.notNull(id, "processoId.id.required");
		
		this.id = id;
	}

	public Long toLong(){
		return id;
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
		
		ProcessoId other = (ProcessoId) obj;
		return sameValueAs(other);
	}
	
	@Override
	public boolean sameValueAs(final ProcessoId other){
		return other != null && this.id.equals(other.id);
	}

	@Override
	public String toString(){
		return id.toString();
	}

}