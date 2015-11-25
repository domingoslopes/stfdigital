package br.jus.stf.plataforma.shared.certification.interfaces.dto;

public class SignerDto {

	private String signerId;

	public SignerDto(String signerId) {
		this.signerId = signerId;
	}

	public String getSignerId() {
		return signerId;
	}

	public void setSignerId(String signerId) {
		this.signerId = signerId;
	}

}
