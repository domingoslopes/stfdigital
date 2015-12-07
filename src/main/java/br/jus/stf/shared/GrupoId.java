package br.jus.stf.shared;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.jus.stf.shared.stereotype.ValueObject;

/**
 * @author Rafael.Alencar
 * @created 26-nov-2015
 */
@Embeddable
public class GrupoId implements ValueObject<GrupoId> {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "SEQ_GRUPO", nullable = false)
	private Long sequencial;

	GrupoId() {

	}
	
	public GrupoId(final Long sequencial){
		Validate.notNull(sequencial, "grupoId.sequencial.required");
		
		this.sequencial = sequencial;
	}
	
	/**
	 * @return
	 */
	public Long toLong(){
		return sequencial;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(sequencial).toHashCode();
	}
	
	@Override
	public boolean equals(final Object o){
		if (this == o) {
			return true;
		}
		
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
	
		GrupoId other = (GrupoId) o;
		return sameValueAs(other);
	}

	/**
	 * 
	 * @param other
	 */
	@Override
	public boolean sameValueAs(final GrupoId other){
		return other != null && sequencial.equals(other.sequencial);
	}
	
}