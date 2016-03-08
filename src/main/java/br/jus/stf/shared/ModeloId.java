package br.jus.stf.shared;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.jus.stf.shared.stereotype.ValueObject;

/**
 * Id do Modelo.
 * 
 * @author Tomas.Godoi
 *
 */
@Embeddable
public class ModeloId implements ValueObject<ModeloId> {

	private static final long serialVersionUID = 1L;

	@Column(name = "SEQ_MODELO", nullable = false)
	private Long sequencial;

	ModeloId() {

	}

	public ModeloId(final Long sequencial) {
		Validate.notNull(sequencial, "modeloId.sequencial.required");

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

		ModeloId other = (ModeloId) o;
		return sameValueAs(other);
	}

	@Override
	public boolean sameValueAs(final ModeloId other) {
		return other != null && this.sequencial.equals(other.sequencial);
	}

}
