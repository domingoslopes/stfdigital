package br.jus.stf.plataforma.shared.certification.signature;

import java.io.InputStream;

import org.apache.commons.lang3.Validate;

public class StreamedDocument {

	private InputStream documentStream;
	
	public StreamedDocument(InputStream documentStream) {
		Validate.notNull(documentStream);
		
		this.documentStream = documentStream;
	}

	public InputStream documentStream() {
		return documentStream;
	}
	
}
