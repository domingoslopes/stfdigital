package br.jus.stf.plataforma.shared.certification.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.jus.stf.shared.stereotype.ValueObject;

@Embeddable
public class CertificateId implements ValueObject<CertificateId> {

	private static final long serialVersionUID = 1L;

	@Column(name = "SEQ_CERTIFICADO_DIGITAL", nullable = false)
	private Long sequencial;

	CertificateId() {

	}

	public CertificateId(final Long sequencial) {
		Validate.notNull(sequencial, "certificateId.sequencial.required");

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

		CertificateId other = (CertificateId) o;
		return sameValueAs(other);
	}

	@Override
	public boolean sameValueAs(final CertificateId other) {
		return other != null && this.sequencial.equals(other.sequencial);
	}
}
