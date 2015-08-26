package br.jus.stf.generico.domain.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
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
@Table(name = "DOCUMENTO")
public class Documento implements Entity<Documento> {

	@Embedded
	@AttributeOverride(name = "id",
		column = @Column(name = "SEQ_DOCUMENTO", insertable = false, updatable = false))
	private DocumentoId documentoId;
	
	@Lob
	@Column(name = "LOB_CONTEUDO")	
	private byte[] conteudo;

	public Documento(final DocumentoId documentoId, final byte[] conteudo){
		Validate.notNull(documentoId);
		Validate.notNull(conteudo);
		
		this.documentoId = documentoId;
		this.conteudo = conteudo;
	}

	public DocumentoId id() {
		return documentoId;
	}

	public byte[] conteudo(){
		return conteudo;
	}
	
	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + ((documentoId == null) ? 0 : documentoId.hashCode());
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
		return other != null && this.documentoId.sameValueAs(other.documentoId);
	}

	//Hibernate
	
	@Id
	@Column(name = "SEQ_DOCUMENTO")
	@SequenceGenerator(name = "DOCUMENTOID", sequenceName = "SEQ_DOCUMENTO", allocationSize = 1)
	@GeneratedValue(generator = "DOCUMENTOID", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	Documento() {
		
	}
	
}