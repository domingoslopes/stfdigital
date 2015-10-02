package br.jus.stf.shared;

import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.stereotype.ValueObject;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 29-set-2015 18:14:46
 */
public class DocumentoTemporarioId implements ValueObject<DocumentoTemporarioId> {

	private static final long serialVersionUID = 1L;
	
	private String id;

	public DocumentoTemporarioId(final String id){
		Validate.notNull(id, "documentoTemporarioId.id.required");
		
		this.id = id;
	}
	
	@Override
	public String toString(){
		return id;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(final Object o){
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
	
		DocumentoTemporarioId other = (DocumentoTemporarioId) o;
		return sameValueAs(other);
	}

	@Override
	public boolean sameValueAs(final DocumentoTemporarioId other){
		return other != null && this.id.equals(other.id);
	}

	DocumentoTemporarioId() {
		
	}
	
}