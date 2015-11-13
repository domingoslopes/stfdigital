package br.jus.stf.plataforma.shared.certification.resources.commands;

public class AssinarCommand {

	private String contextId;
	private String signature;

	public AssinarCommand(String contextId, String signature) {
		
	}
	
	public String getContextId() {
		return contextId;
	}

	public void setContextId(String contextId) {
		this.contextId = contextId;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

}
