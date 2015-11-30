package br.jus.stf.plataforma.shared.certification.interfaces.dto;

import org.apache.commons.lang3.Validate;

public class SignedDocumentDto {

	private Long documentId;

	public SignedDocumentDto(Long id) {
		Validate.notNull(id);

		documentId = id;
	}

	public Long getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}

}
