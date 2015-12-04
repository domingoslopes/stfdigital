package br.jus.stf.plataforma.shared.certification.domain.model.signature;

public interface PdfSigningStrategy extends SigningStrategy {

	void prepareStrategyWith(PdfSigningSpecification pdfSigningSpecification);
	
}
