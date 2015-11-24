package br.jus.stf.plataforma.shared.certification.domain.model;

import br.jus.stf.plataforma.shared.certification.signature.DocumentSignerId;

public class DocumentSigner {

	private DocumentSignerId id;
	private SigningStrategy<SigningSpecification> strategy;
	private SigningSpecification spec;
	
	public DocumentSigner(DocumentSignerId id) {
		
		this.id = id;
	}
	
	public DocumentSignerId id() {
		return id;
	}

	public PreSignature preSign(SigningDocument document) {
		return strategy.preSign(document, spec);
	}
	
}
