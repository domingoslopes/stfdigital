package br.jus.stf.plataforma.shared.certification.domain.model.signature;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.jus.stf.shared.stereotype.ValueObject;

/**
 * Representa um resumo de dados prontos para serem assinados.
 * 
 * @author Tomas.Godoi
 *
 */
public class PreSignature implements ValueObject<PreSignature> {

	private static final long serialVersionUID = 1L;

	private AuthenticatedAttributes auth;
	private HashToSign hash;
	private HashType hashType;

	public PreSignature(AuthenticatedAttributes auth, HashToSign hash, HashType hashType) {
		Validate.notNull(auth);
		Validate.notNull(hash);
		Validate.notNull(hashType);

		this.auth = auth;
		this.hash = hash;
		this.hashType = hashType;
	}

	public AuthenticatedAttributes auth() {
		return auth;
	}

	public HashToSign hash() {
		return hash;
	}

	public HashType hashType() {
		return hashType;
	}

	@Override
	public boolean sameValueAs(PreSignature other) {
		return other != null && new EqualsBuilder().append(auth, other.auth).append(hash, other.hash)
				.append(hashType, other.hashType).isEquals();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;

		PreSignature tObj = (PreSignature) obj;

		return sameValueAs(tObj);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(auth).append(hash).append(hashType).toHashCode();
	}

}
