package br.jus.stf.plataforma.documentos.domain.model;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.stereotype.ValueObject;

/**
 * @author Lucas.Rodrigues
 *
 */
public class DocumentoDownload implements ValueObject<DocumentoDownload> {

	private static final long serialVersionUID = -2253600773759421258L;
	
	private InputStream stream;
	private Long tamanho;
	
	public DocumentoDownload(final InputStream stream, final Long tamanho) {
		Validate.notNull(stream, "documentodownload.stream.required");
		Validate.notNull(tamanho, "documentodownload.tamanho.required");
		
		this.stream = stream;
		this.tamanho = tamanho;
	}
	
	public InputStream stream() {
		return stream;
	}
	
	public Long tamanho() {
		return tamanho;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((stream == null) ? 0 : stream.hashCode());
		result = prime * result + ((tamanho == null) ? 0 : tamanho.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		DocumentoDownload other = (DocumentoDownload) obj;
		return sameValueAs(other);
	}

	@Override
	public boolean sameValueAs(DocumentoDownload other) {
		try {
			return IOUtils.contentEquals(stream, other.stream) &&
					tamanho.equals(other.tamanho);
		} catch (IOException e) {
			return false;
		}
	}

}
