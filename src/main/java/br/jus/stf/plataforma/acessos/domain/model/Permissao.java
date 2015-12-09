package br.jus.stf.plataforma.acessos.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.jus.stf.shared.stereotype.ValueObject;

@Entity
@Table(name = "PERMISSAO", schema = "PLATAFORMA", uniqueConstraints = @UniqueConstraint(columnNames = {"SEQ_TIPO_INFORMACAO", "SEQ_SEGMENTO", "TIP_PERMISSAO"}))
public class Permissao implements ValueObject<Permissao> {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "SEQ_PERMISSAO")
	@SequenceGenerator(name = "PERMISSAOID", sequenceName = "PLATAFORMA.SEQ_PERMISSAO", allocationSize = 1)
	@GeneratedValue(generator = "PERMISSAOID", strategy=GenerationType.SEQUENCE)
	private Long sequencial;
	
	@Column(name = "TIP_PERMISSAO", nullable = false)
	@Enumerated(EnumType.STRING)
	private TipoPermissao tipo;
	
	@ManyToOne
	@JoinColumn(name = "SEQ_SEGMENTO", referencedColumnName = "SEQ_SEGMENTO")
	private Segmento segmento;
	
	@ManyToOne
	@JoinColumn(name = "SEQ_TIPO_INFORMACAO", referencedColumnName = "SEQ_TIPO_INFORMACAO", nullable = false)
	private TipoInformacao tipoInformacao;
	
	Permissao() {
		
	}

	private Permissao(final Long sequencial, final TipoPermissao tipo) {
		Validate.notNull(sequencial, "permissao.sequencial.required");
		Validate.notNull(tipo, "permissao.tipo.required");
		
		this.sequencial = sequencial;
		this.tipo = tipo;
	}
	
	public Permissao(final Long sequencial, final TipoPermissao tipoPermissao, final TipoInformacao tipoInformacao) {
		this(sequencial, tipoPermissao);
		Validate.notNull(tipoInformacao, "permissao.tipoInformacao.required");

		this.tipoInformacao = tipoInformacao;
	}
	
	public Permissao(final Long sequencial, final TipoPermissao tipoPermissao, final Segmento segmento) {
		this(sequencial, tipoPermissao);
		Validate.notNull(segmento, "permissao.segmento.required");
		Validate.notNull(segmento.tipo(), "permissao.tipoInformacao.required");
		
		this.segmento = segmento;
		this.tipoInformacao = segmento.tipo();
	}
	
	public Long toLong() {
		return sequencial;
	}
	
	public TipoInformacao tipoInformacao() {
		return tipoInformacao;
	}
	
	public Segmento segmento() {
		return segmento;
	}
	
	public TipoPermissao tipo() {
		return tipo;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(segmento).hashCode();
	}
	
	@Override
	public boolean equals(Object obj){
		
		if (this == obj) {
			return true;
		}
		
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
	
		Permissao other = (Permissao) obj;
		
		return sequencial.equals(other.sequencial);
	}
	
	@Override
	public boolean sameValueAs(final Permissao other) {
		return other != null
				&& tipoInformacao.equals(tipoInformacao)
				&& tipo.equals(other.tipo)
				&& ((segmento == null && other.segmento == null) || segmento.equals(other.segmento));
	}

}
