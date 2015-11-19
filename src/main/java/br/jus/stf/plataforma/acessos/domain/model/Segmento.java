package br.jus.stf.plataforma.acessos.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.stereotype.ValueObject;

@Entity
@Table(name = "SEGMENTO", schema = "PLATAFORMA")
public class Segmento implements ValueObject<Segmento> {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "SEQ_SEGMENTO")
	@SequenceGenerator(name = "SEGMENTOID", sequenceName = "PLATAFORMA.SEQ_SEGMENTO", allocationSize = 1)
	@GeneratedValue(generator = "SEGMENTOID", strategy=GenerationType.SEQUENCE)
	private Long sequencial;
	
	@Column(name = "NOM_SEGMENTO", nullable = false)
	private String nome;
	
	@ManyToOne
	@JoinColumn(name = "SEQ_TIPO_INFORMACAO", referencedColumnName = "SEQ_TIPO_INFORMACAO", nullable = false)
	private TipoInformacao tipoInformacao;
	
	@ManyToOne
	@JoinColumn(name = "SEQ_TIPO_SEGMENTO", referencedColumnName = "SEQ_TIPO_SEGMENTO")
	private TipoSegmento tipoSegmento;
	
	Segmento() {
		
	}
	
	public Segmento(final Long sequencial, final String nome, final TipoInformacao tipoInformacao) {
		Validate.notNull(sequencial, "segmento.sequencial.required");
		Validate.notBlank(nome, "segmento.nome.required");
		Validate.notNull(tipoInformacao, "segmento.tipoInformacao.required");
		
		this.sequencial = sequencial;
		this.nome = nome;
		this.tipoInformacao = tipoInformacao;
	}
	
	public Segmento(final Long sequencial, final String nome, final TipoInformacao tipoInformacao, final TipoSegmento tipoSegmento) {
		this(sequencial, nome, tipoInformacao);
		
		Validate.notNull(tipoSegmento, "segmento.tipoSegmento.required");
		
		this.tipoSegmento = tipoSegmento;
	}
	
	public Long toLong() {
		return sequencial;
	}
	
	public String nome() {
		return nome;
	}
	
	public TipoInformacao tipoInformacao() {
		return tipoInformacao;
	}
	
	public TipoSegmento tipoSegmento() {
		return tipoSegmento;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = prime * result + ((sequencial == null) ? 0 : sequencial.hashCode());
		
		return result;
	}
	
	@Override
	public boolean equals(Object obj){
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
	
		Segmento other = (Segmento) obj;
		return sameValueAs(other);
	}

	@Override
	public boolean sameValueAs(final Segmento other) {
		return other != null && sequencial.equals(other.sequencial);
	}
	

}
