package br.jus.stf.plataforma.shared.certification.interfaces.commands;

public class PreSignCommand {

	private String tempDocId;
	private String certificate;

	public String getTempDocId() {
		return tempDocId;
	}

	public void setTempDocId(String tempDocId) {
		this.tempDocId = tempDocId;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

}
