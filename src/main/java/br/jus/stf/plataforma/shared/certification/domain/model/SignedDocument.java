package br.jus.stf.plataforma.shared.certification.domain.model;

import java.io.InputStream;

public interface SignedDocument {

	InputStream stream();
	
	byte[] bytes();
	
	DocumentSignature signature();

}
