package br.jus.stf.plataforma.shared.certification.interfaces.commands;

public class ProvideToSignCommand {

	private String contextId;
	private Long documentId;

	public String getContextId() {
		return contextId;
	}

	public void setContextId(String contextId) {
		this.contextId = contextId;
	}

	public Long getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}

}
