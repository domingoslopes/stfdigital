package br.jus.stf.processamentoinicial.suporte.domain.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.jus.stf.shared.DocumentoId;
import br.jus.stf.shared.stereotype.ValueObject;

@MappedSuperclass
public abstract class Peca implements ValueObject<Peca> {
	
	private static final long serialVersionUID = 1L;
	
	@Embedded
	@Column(nullable = false)
	private DocumentoId documento;
	
	@ManyToOne
	@JoinColumn(name = "SEQ_TIPO_PECA", nullable = false)
	private TipoPeca tipo;
	
	@Column(name = "DSC_PECA", nullable = false)
	private String descricao;
	
	@Column(name = "NUM_ORDEM_PECA")
	private Long numeroOrdem;
	
	protected Peca() {

	}
	
	protected Peca(final DocumentoId documento, final TipoPeca tipo, final String descricao) {
		Validate.notNull(documento, "peca.documento.required");
		Validate.notNull(tipo, "peca.tipo.required");
		Validate.notBlank(descricao, "peca.descricao.required");
		
		this.documento = documento;
		this.tipo = tipo;
		this.descricao = descricao;
	}
	
	public abstract Long toLong();
	
	public DocumentoId documento() {
		return this.documento;
	}
	
	public TipoPeca tipo() {
		return this.tipo;
	}
	
	public String descricao() {
		return this.descricao;
	}
	
	public Long numeroOrdem() {
		return numeroOrdem;
	}
	
	public void numerarOrdem(Long numeroOrdem) {
		this.numeroOrdem = numeroOrdem;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(documento).append(tipo).append(numeroOrdem).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
	    if (this == obj) {
	    	return true;
	    }
	    
	    if (obj == null || !(obj instanceof Peca)) {
	    	return false;
	    }
	    
	    final Peca other = (Peca) obj;
	    return sameValueAs(other);
	}
	
	@Override
	public boolean sameValueAs(final Peca other){
		return other != null && this.documento.sameValueAs(other.documento) && this.tipo.sameValueAs(other.tipo) && this.numeroOrdem.equals(other.numeroOrdem);
	}

}
