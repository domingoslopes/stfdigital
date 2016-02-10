package br.jus.stf.plataforma.documentos.domain.model;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.stereotype.ValueObject;

/**
 * @author Lucas.Rodrigues
 *
 */
public class ConteudoDocumento implements ValueObject<ConteudoDocumento> {

	private static final long serialVersionUID = -2253600773759421258L;
	
	private byte[] bytes;
	private Long tamanho;
	
	public ConteudoDocumento(final byte[] bytes, final Long tamanho) {
		Validate.notNull(bytes, "documentodownload.bytes.required");
		Validate.notNull(tamanho, "documentodownload.tamanho.required");
		
		this.bytes = bytes;
		this.tamanho = tamanho;
	}
	
	public InputStream stream() {
		return new ByteArrayInputStream(bytes);
	}
	
	public Long tamanho() {
		return tamanho;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bytes == null) ? 0 : bytes.hashCode());
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
		ConteudoDocumento other = (ConteudoDocumento) obj;
		return sameValueAs(other);
	}

	@Override
	public boolean sameValueAs(ConteudoDocumento other) {
		return bytes.equals(other.bytes) &&
				tamanho.equals(other.tamanho);
	}

}
