package br.jus.stf.plataforma.documentos.domain.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.DocumentoId;
import br.jus.stf.shared.stereotype.Entity;

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
	
	@Column(name = "NUM_CONTEUDO", nullable = false)	
	private String numeroConteudo;

	public Documento(final DocumentoId id, final String numeroConteudo) {
		Validate.notNull(id, "documento.id.required");
		Validate.notBlank(numeroConteudo, "documento.numeroConteudo.required");
		
		this.id = id;
		this.numeroConteudo = numeroConteudo;
	}

	public DocumentoId id() {
		return id;
	}

	public String numeroConteudo(){
		return numeroConteudo;
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