package br.jus.stf.plataforma.shared.certification.domain.model.signature;

import br.jus.stf.plataforma.shared.certification.domain.model.DocumentType;

public class PDFSigningSpecification implements SigningSpecification {

	private PDFSigningStrategy strategy;
	private String reason;
	private HashType hashType;

	public PDFSigningSpecification(PDFSigningStrategy strategy, String reason, HashType hashType) {
		this.strategy = strategy;
		this.reason = reason;
		this.hashType = hashType;

		this.strategy.prepareStrategyWith(this);
	}

	@Override
	public DocumentType documentType() {
		return DocumentType.PDF;
	}

	@Override
	public PDFSigningStrategy strategy() {
		return strategy;
	}

	public String reason() {
		return reason;
	}

	public HashType hashType() {
		return hashType;
	}

}
