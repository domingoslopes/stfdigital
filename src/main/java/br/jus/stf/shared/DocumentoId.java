package br.jus.stf.shared;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.jus.stf.shared.stereotype.ValueObject;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:33:46
 */
@Embeddable
public class DocumentoId implements ValueObject<DocumentoId> {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "SEQ_DOCUMENTO", nullable = false)
	private Long sequencial;

	DocumentoId() {

	}
	
	public DocumentoId(final Long sequencial){
		Validate.notNull(sequencial, "documentoId.sequencial.required");
		
		this.sequencial = sequencial;
	}

	public Long toLong(){
		return sequencial;
	}
	
	@Override
	public String toString(){
		return sequencial.toString();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(sequencial).toHashCode();
	}
	
	@Override
	public boolean equals(final Object o){
		if (this == o) {
			return true;
		}
		
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
	
		DocumentoId other = (DocumentoId) o;
		return sameValueAs(other);
	}

	@Override
	public boolean sameValueAs(final DocumentoId other){
		return other != null && this.sequencial.equals(other.sequencial);
	}
	
}