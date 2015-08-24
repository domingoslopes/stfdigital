package br.jus.stf.shared.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import br.jus.stf.shared.domain.stereotype.ValueObject;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:33:46
 */
@Embeddable
public class ProcessInstanceId implements ValueObject<ProcessInstanceId>{

	private static final long serialVersionUID = 1L;
	
	@Column(name = "NUM_PROCESS_INSTANCE", nullable = false)
	private Long id;

	public ProcessInstanceId(final Long id){
		this.id = id;
	}

	public Long id(){
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
	public boolean equals(final Object o){
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
	
		ProcessInstanceId other = (ProcessInstanceId) o;
		return sameValueAs(other);
	}

	@Override
	public boolean sameValueAs(final ProcessInstanceId other){
		return other != null && this.id.equals(other.id);
	}

	public String toString(){
		return id.toString();
	}

	//Hibernate
	
	ProcessInstanceId() {
		
	}
	
}