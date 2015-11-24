package br.jus.stf.plataforma.shared.certification.domain.model;

public interface PDFSigningStrategy extends SigningStrategy {

	void prepareStrategyWith(PDFSigningSpecification pdfSigningSpecification);
	
}
