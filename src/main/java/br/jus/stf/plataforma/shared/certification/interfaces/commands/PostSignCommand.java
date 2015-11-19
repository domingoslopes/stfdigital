package br.jus.stf.plataforma.shared.certification.interfaces.commands;

public class PostSignCommand {

	private String contextId;
	private String signatureAsHex;

	public String getContextId() {
		return contextId;
	}

	public void setContextId(String contextId) {
		this.contextId = contextId;
	}

	public String getSignatureAsHex() {
		return signatureAsHex;
	}

	public void setSignatureAsHex(String signatureAsHex) {
		this.signatureAsHex = signatureAsHex;
	}

}
