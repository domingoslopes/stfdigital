package br.jus.stf.plataforma.shared.certification.domain.model;

public interface SignedDocument {
	
	Document document();
	
	DocumentSignature signature();

}
