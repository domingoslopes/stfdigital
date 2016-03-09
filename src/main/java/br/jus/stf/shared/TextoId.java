package br.jus.stf.shared;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.jus.stf.shared.stereotype.ValueObject;

/**
 * Id do Texto.
 * 
 * @author Tomas.Godoi
 *
 */
@Embeddable
public class TextoId implements ValueObject<TextoId> {

	private static final long serialVersionUID = 1L;

	@Column(name = "SEQ_TEXTO", nullable = false)
	private Long sequencial;

	TextoId() {

	}

	public TextoId(final Long sequencial) {
		Validate.notNull(sequencial, "textoId.sequencial.required");
		
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
		return new HashCodeBuilder().append(sequencial).toHashCode();
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}

		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		TextoId other = (TextoId) o;
		return sameValueAs(other);
	}

	@Override
	public boolean sameValueAs(final TextoId other) {
		return other != null && this.sequencial.equals(other.sequencial);
	}

}
