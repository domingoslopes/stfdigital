package br.jus.stf.processamentoinicial.suporte.domain.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.jus.stf.shared.TipoDocumentoId;
import br.jus.stf.shared.stereotype.ValueObject;

@Entity
@Table(name = "TIPO_PECA", schema = "AUTUACAO")
public class TipoPeca implements ValueObject<TipoPeca> {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	@AttributeOverride(name = "sequencial", column = @Column(name = "SEQ_TIPO_PECA"))
	private TipoDocumentoId id;
	
	@Column(name = "NOM_TIPO_PECA", nullable = false)
	private String nome;
	
	TipoPeca() {

	}
	
	public TipoPeca(final TipoDocumentoId id, final String nome) {
		Validate.notNull(id, "tipoPeca.id.required");
		Validate.notBlank(nome, "tipoPeca.nome.required");
		
		this.id = id;
		this.nome = nome;
	}
	
	public TipoDocumentoId id() {
		return id;
	}
	
	public String nome() {
		return nome;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
	    if (this == obj) {
	    	return true;
	    }
	    
	    if (obj == null || !(obj instanceof TipoPeca)) {
	    	return false;
	    }
	    
	    final TipoPeca other = (TipoPeca) obj;
	    return sameValueAs(other);
	}
	
	@Override
	public boolean sameValueAs(final TipoPeca other){
		return other != null && this.id.sameValueAs(other.id);
	}
	
}
