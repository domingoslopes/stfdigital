package br.jus.stf.plataforma.shared.certification.domain.model;

import br.jus.stf.shared.stereotype.ValueObject;

public enum HashType implements ValueObject<HashType> {

	SHA1, SHA224, SHA256, SHA384, SHA512;

	@Override
	public boolean sameValueAs(HashType other) {
		return this.equals(other);
	}

}
