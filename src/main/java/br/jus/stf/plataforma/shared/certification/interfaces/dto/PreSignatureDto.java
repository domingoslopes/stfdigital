package br.jus.stf.plataforma.shared.certification.interfaces.dto;

import br.jus.stf.plataforma.shared.certification.support.HashType;

public class PreSignatureDto {

	private String signatureContextId;
	private String hash;
	private HashType hashType;

	public String getSignatureContextId() {
		return signatureContextId;
	}

	public void setSignatureContextId(String signatureContextId) {
		this.signatureContextId = signatureContextId;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public HashType getHashType() {
		return hashType;
	}

	public void setHashType(HashType hashType) {
		this.hashType = hashType;
	}

}
