package br.jus.stf.plataforma.documentos.domain.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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
	
	@Column(name = "QTD_PAGINAS")
	private Integer quantidadePaginas;
	
	Documento() {

	}

	public Documento(final DocumentoId id, final String numeroConteudo) {
		Validate.notNull(id, "documento.id.required");
		Validate.notBlank(numeroConteudo, "documento.numeroConteudo.required");
		
		this.id = id;
		this.numeroConteudo = numeroConteudo;
	}

	@Override
	public DocumentoId id() {
		return id;
	}

	public String numeroConteudo(){
		return numeroConteudo;
	}
	
	public Integer quantidadePaginas() {
		return quantidadePaginas;
	}
	
	@Override
	public int hashCode(){
		return new HashCodeBuilder().append(id).toHashCode();
	}
	
	@Override
	public boolean equals(final Object o){
		if (this == o) {
			return true;
		}
		
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
	
		Documento other = (Documento) o;
		return sameIdentityAs(other);
	}
	
	@Override
	public boolean sameIdentityAs(final Documento other) {
		return other != null && this.id.sameValueAs(other.id);
	}
	
}