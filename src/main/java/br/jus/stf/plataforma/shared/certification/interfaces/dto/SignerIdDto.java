package br.jus.stf.plataforma.shared.certification.interfaces.dto;

public class SignerIdDto {

	private String signerId;

	public SignerIdDto(String signerId) {
		this.signerId = signerId;
	}

	public String getSignerId() {
		return signerId;
	}

	public void setSignerId(String signerId) {
		this.signerId = signerId;
	}

}
