package br.jus.stf.plataforma.shared.certification.interfaces.dto;

import br.jus.stf.plataforma.shared.certification.domain.model.PreSignature;

public class PreSignatureDto {

	private String hash;
	private String hashType;

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getHashType() {
		return hashType;
	}

	public void setHashType(String hashType) {
		this.hashType = hashType;
	}

	public static PreSignatureDto from(PreSignature preSignature) {
		PreSignatureDto dto = new PreSignatureDto();
		dto.setHash(preSignature.hash().hashAsHex());
		dto.setHashType(preSignature.hashType().toString());
		return dto;
	}

}
