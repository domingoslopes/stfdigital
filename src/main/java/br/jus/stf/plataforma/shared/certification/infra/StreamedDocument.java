package br.jus.stf.plataforma.shared.certification.infra;

import java.io.InputStream;

import org.apache.commons.lang3.Validate;

import br.jus.stf.plataforma.shared.certification.domain.model.SigningDocument;

public class StreamedDocument implements SigningDocument {

	private InputStream documentStream;
	
	public StreamedDocument(InputStream documentStream) {
		Validate.notNull(documentStream);
		
		this.documentStream = documentStream;
	}

	@Override
	public InputStream stream() {
		return documentStream;
	}
	
}
