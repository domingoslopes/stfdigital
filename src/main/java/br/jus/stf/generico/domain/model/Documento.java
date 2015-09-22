package br.jus.stf.generico.domain.model;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.domain.model.DocumentoId;
import br.jus.stf.shared.domain.stereotype.Entity;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:34:02
 */
@javax.persistence.Entity
@Table(name = "DOCUMENTO", schema = "CORPORATIVO")
public class Documento implements Entity<Documento, DocumentoId> {

	@EmbeddedId
	private DocumentoId id;
	
	@Lob
	@Column(name = "BIN_CONTEUDO")	
	private Blob conteudo;

	public Documento(final DocumentoId id, final Blob conteudo) {
		Validate.notNull(id);
		Validate.notNull(conteudo);
		
		this.id = id;
		this.conteudo = conteudo;
	}

	public DocumentoId id() {
		return id;
	}

	public Blob conteudo(){
		return conteudo;
	}
	
	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(final Object o){
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
	
		Documento other = (Documento) o;
		return sameIdentityAs(other);
	}
	
	@Override
	public boolean sameIdentityAs(final Documento other) {
		return other != null && this.id.sameValueAs(other.id);
	}

	//Hibernate
	
	Documento() {
		
	}
}