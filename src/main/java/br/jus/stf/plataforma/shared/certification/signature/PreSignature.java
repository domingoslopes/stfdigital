package br.jus.stf.plataforma.shared.certification.signature;

import org.apache.commons.lang3.Validate;

import br.jus.stf.plataforma.shared.certification.support.HashType;
import br.jus.stf.plataforma.shared.certification.support.AuthenticatedAttributes;
import br.jus.stf.plataforma.shared.certification.support.HashToSign;

public class PreSignature {

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

}
