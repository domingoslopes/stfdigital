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

import br.jus.stf.shared.SegmentoId;

@Entity
@Table(name = "SEGMENTO", schema = "PLATAFORMA", uniqueConstraints = @UniqueConstraint(columnNames = {"NOM_SEGMENTO", "SEQ_TIPO_INFORMACAO"}))
public class Segmento implements br.jus.stf.shared.stereotype.Entity<Segmento, SegmentoId> {
	
	@EmbeddedId
	private SegmentoId id;
	
	@Column(name = "NOM_SEGMENTO", nullable = false)
	private String nome;
	
	@ManyToOne
	@JoinColumn(name = "SEQ_TIPO_INFORMACAO", referencedColumnName = "SEQ_TIPO_INFORMACAO", nullable = false)
	private TipoInformacao tipo;
	
	Segmento() {
		
	}
	
	public Segmento(final SegmentoId id, final String nome, final TipoInformacao tipo) {
		Validate.notNull(id, "segmento.id.required");
		Validate.notBlank(nome, "segmento.nome.required");
		Validate.notNull(tipo, "segmento.tipo.required");
		
		this.id = id;
		this.nome = nome;
		this.tipo = tipo;
	}
	
	public String nome() {
		return nome;
	}
	
	public TipoInformacao tipo() {
		return tipo;
	}
	
	@Override
	public SegmentoId id() {
		return id;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).hashCode();
	}
	
	@Override
	public boolean equals(Object obj){
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
	
		Segmento other = (Segmento) obj;
		return sameIdentityAs(other);
	}

	@Override
	public boolean sameIdentityAs(final Segmento other) {
		return other != null
				&& id.equals(other.id);
	}	

}
