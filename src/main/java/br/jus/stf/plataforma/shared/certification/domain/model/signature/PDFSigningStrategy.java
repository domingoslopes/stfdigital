package br.jus.stf.plataforma.shared.certification.domain.model.signature;

public interface PDFSigningStrategy extends SigningStrategy {

	void prepareStrategyWith(PDFSigningSpecification pdfSigningSpecification);
	
}
