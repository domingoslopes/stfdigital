package br.jus.stf.plataforma.shared.certification.signature;

import org.apache.commons.lang3.Validate;

public class SignedDocument {

	private byte[] bytes;

	public SignedDocument(byte[] bytes) {
		Validate.notNull(bytes);

		this.bytes = bytes;
	}

	public byte[] asBytes() {
		return bytes;
	}

}
