package br.jus.stf.shared;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.jus.stf.shared.stereotype.ValueObject;

/**
 * Id do Tipo do Modelo.
 * 
 * @author Tomas.Godoi
 *
 */
@Embeddable
public class TipoModeloId implements ValueObject<TipoModeloId> {

	private static final long serialVersionUID = 1L;

	@Column(name = "SEQ_TIPO_MODELO", nullable = false)
	private Long sequencial;

	TipoModeloId() {

	}

	public TipoModeloId(final Long sequencial) {
		Validate.notNull(sequencial, "tipoModeloId.sequencial.required");
		
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

		TipoModeloId other = (TipoModeloId) o;
		return sameValueAs(other);
	}

	@Override
	public boolean sameValueAs(final TipoModeloId other) {
		return other != null && this.sequencial.equals(other.sequencial);
	}

}
