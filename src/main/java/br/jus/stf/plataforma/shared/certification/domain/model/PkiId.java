package br.jus.stf.plataforma.shared.certification.domain.model;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.jus.stf.shared.stereotype.ValueObject;

public class PkiId implements ValueObject<PkiId> {

	private static final long serialVersionUID = 1L;

	private String id;

	public PkiId(String id) {
		Validate.notEmpty(id, "pkiId.id.required");

		this.id = id;
	}

	public String id() {
		return id;
	}

	@Override
	public boolean sameValueAs(PkiId other) {
		return other != null && new EqualsBuilder().append(id, other.id).isEquals();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;

		PkiId tObj = (PkiId) obj;

		return sameValueAs(tObj);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).toHashCode();
	}

}
