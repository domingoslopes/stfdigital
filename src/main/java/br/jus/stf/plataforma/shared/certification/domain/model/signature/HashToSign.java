package br.jus.stf.plataforma.shared.certification.domain.model.signature;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.jus.stf.shared.stereotype.ValueObject;

public class HashToSign implements ValueObject<HashToSign> {

	private static final long serialVersionUID = 1L;

	private byte[] hashAsBytes;

	public HashToSign(String hashAsHex) {
		Validate.notEmpty(hashAsHex);

		try {
			this.hashAsBytes = Hex.decodeHex(hashAsHex.toCharArray());
		} catch (DecoderException e) {
			throw new RuntimeException("Erro ao decodificar hash em hexadecimal.", e);
		}
	}

	public HashToSign(byte[] hashAsBytes) {
		Validate.notNull(hashAsBytes);

		this.hashAsBytes = hashAsBytes;
	}

	public String hashAsHex() {
		return Hex.encodeHexString(hashAsBytes);
	}

	public byte[] hashAsBytes() {
		return hashAsBytes;
	}

	@Override
	public boolean sameValueAs(HashToSign other) {
		return other != null && new EqualsBuilder().append(hashAsBytes, other.hashAsBytes).isEquals();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;

		HashToSign tObj = (HashToSign) obj;

		return sameValueAs(tObj);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(hashAsBytes).toHashCode();
	}
	
}
