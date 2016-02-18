package br.jus.stf.processamentoinicial.suporte.domain.model;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
	
	@Column(name = "TIP_VISIBILIDADE")
	@Enumerated(EnumType.STRING)
	private Visibilidade visibilidade = Visibilidade.PUBLICO;
	
	@Column(name = "TIP_SITUACAO")
	@Enumerated(EnumType.STRING)
	private Situacao situacao = Situacao.JUNTADA;
	
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
	
	protected Peca(final DocumentoId documento, final TipoPeca tipo, final String descricao,
			final Visibilidade visibilidade) {
		this(documento, tipo, descricao);
		
		Validate.notNull(visibilidade, "peca.visibilidade.required");
		
		this.visibilidade = visibilidade;
	}
	
	protected Peca(final DocumentoId documento, final TipoPeca tipo, final String descricao,
			final Situacao situacao) {
		this(documento, tipo, descricao);
		
		Validate.notNull(situacao, "peca.situacao.required");
		
		this.situacao = situacao;
	}
	
	protected Peca(final DocumentoId documento, final TipoPeca tipo, final String descricao,
			final Visibilidade visibilidade, final Situacao situacao) {
		this(documento, tipo, descricao);
		
		this.visibilidade = Optional.ofNullable(visibilidade).orElse(Visibilidade.PUBLICO);
		this.situacao = Optional.ofNullable(situacao).orElse(Situacao.JUNTADA);
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
	
	public Visibilidade visibilidade() {
		return visibilidade;
	}
	
	public void alterarSituacao(Situacao situacao) {
		this.situacao = situacao;
	}
	
	public Situacao situacao() {
		return situacao;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(documento).append(tipo).append(numeroOrdem)
				.append(visibilidade).append(situacao).hashCode();
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
		return other != null && documento.sameValueAs(other.documento) && tipo.sameValueAs(other.tipo)
				&& numeroOrdem.equals(other.numeroOrdem) && visibilidade.sameValueAs(other.visibilidade)
				&& situacao.sameValueAs(other.situacao);
	}

}
