package br.jus.stf.shared;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.jus.stf.shared.stereotype.ValueObject;

/**
 * @author Rafael.Alencar
 * @created 04-dez-2015
 */
@Embeddable
public class InformacaoId implements ValueObject<InformacaoId> {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "SEQ_INFORMACAO", nullable = false)
	private Long sequencial;

	InformacaoId() {

	}
	
	public InformacaoId(final Long sequencial){
		Validate.notNull(sequencial, "informacaoId.sequencial.required");
		
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
	public boolean equals(final Object o){
		if (this == o) {
			return true;
		}
		
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
	
		InformacaoId other = (InformacaoId) o;
		return sameValueAs(other);
	}

	@Override
	public boolean sameValueAs(final InformacaoId other){
		return other != null && this.sequencial.equals(other.sequencial);
	}
	
}