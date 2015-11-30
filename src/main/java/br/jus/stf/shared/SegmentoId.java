package br.jus.stf.shared;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.jus.stf.shared.stereotype.ValueObject;

/**
 * @author Rafael.Alencar
 * @created 27-nov-2015
 */
@Embeddable
public class SegmentoId implements ValueObject<SegmentoId> {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "SEQ_SEGMENTO", nullable = false)
	private Long sequencial;

	SegmentoId() {

	}
	
	public SegmentoId(final Long sequencial){
		Validate.notNull(sequencial, "segmentoId.sequencial.required");
		
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
	
		SegmentoId other = (SegmentoId) o;
		return sameValueAs(other);
	}

	/**
	 * 
	 * @param other
	 */
	@Override
	public boolean sameValueAs(final SegmentoId other){
		return other != null && sequencial.equals(other.sequencial);
	}
	
}