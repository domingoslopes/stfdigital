package br.jus.stf.plataforma.acessos.domain.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.jus.stf.shared.InformacaoId;

@Entity
@Table(name = "INFORMACAO", schema = "PLATAFORMA", uniqueConstraints = @UniqueConstraint(columnNames = {"SEQ_TIPO_INFORMACAO", "SEQ_SEGMENTO", "COD_IDENTIDADE"}))
public class Informacao implements br.jus.stf.shared.stereotype.Entity<Informacao, InformacaoId> {
	
	@EmbeddedId
	private InformacaoId informacao;
	
	@ManyToOne
	@JoinColumn(name = "SEQ_TIPO_INFORMACAO", nullable = false)
	private TipoInformacao tipo;
	
	@ManyToOne
	@JoinColumn(name = "SEQ_SEGMENTO")
	private Segmento segmento;
	
	@Column(name = "COD_IDENTIDADE", nullable = false)
	private String identidade;
	
	Informacao() {
		
	}
	
	private Informacao(final InformacaoId id, final String identidade) {
		Validate.notNull(id, "informacao.id.required");
		Validate.notBlank(identidade, "informacao.identidade.required");
		
		this.informacao = id;
		this.identidade = identidade;
	}
	
	public Informacao(final InformacaoId id, final TipoInformacao tipo, final String identidade) {
		this(id, identidade);
		Validate.notNull(tipo, "informacao.tipo.required");
		
		this.tipo = tipo;
	}
	
	public Informacao(final InformacaoId id, final Segmento segmento, final String identidade) {
		this(id, identidade);
		
		Validate.notNull(segmento, "informacao.segmento.required");
		Validate.notNull(segmento.tipo(), "informacao.tipo.required");
		
		this.segmento = segmento;
		this.tipo = segmento.tipo();
	}
	
	public TipoInformacao tipo() {
		return tipo;
	}
	
	public Segmento segmento() {
		return segmento;
	}
	
	public String identidade() {
		return identidade;
	}
	
	@Override
	public InformacaoId id() {
		return informacao;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(informacao).hashCode();
	}
	
	@Override
	public boolean equals(Object obj){
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
	
		Informacao other = (Informacao) obj;
		
		return sameIdentityAs(other);
	}

	@Override
	public boolean sameIdentityAs(final Informacao other) {
		return other != null
				&& informacao.sameValueAs(other.informacao);
	}

}
