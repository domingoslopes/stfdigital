package br.jus.stf.shared;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.jus.stf.shared.stereotype.ValueObject;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 08-jan-2016
 */
@Embeddable
public class TeseId implements ValueObject<TeseId>{

	private static final long serialVersionUID = 1L;
	
	@Column(name = "SEQ_TESE", nullable = false)
	private Long sequencia;

	TeseId() {

	}

	public TeseId(final Long sequencia){
		Validate.notNull(sequencia, "teseId.sequencia.required");
		
		this.sequencia = sequencia;
	}

	public Long toLong(){
		return sequencia;
	}
	
	@Override
	public String toString(){
		return sequencia.toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(sequencia).toHashCode();
	}
	
	@Override
	public boolean equals(final Object o){
		
		if (this == o) {
			return true;
		}
		
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		
		TeseId other = (TeseId) o;
		
		return sameValueAs(other);
	}

	@Override
	public boolean sameValueAs(final TeseId other){
		return other != null && sequencia.equals(other.sequencia);
	}
	
}