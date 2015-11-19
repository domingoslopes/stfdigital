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

import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.stereotype.ValueObject;

@Entity
@Table(name = "PERMISSAO", schema = "PLATAFORMA")
public class Permissao implements ValueObject<Permissao> {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "SEQ_PERMISSAO")
	@SequenceGenerator(name = "PERMISSAOID", sequenceName = "PLATAFORMA.SEQ_PERMISSAO", allocationSize = 1)
	@GeneratedValue(generator = "PERMISSAOID", strategy=GenerationType.SEQUENCE)
	private Long sequencial;
	
	@ManyToOne
	@JoinColumn(name = "SEQ_SEGMENTO", referencedColumnName = "SEQ_SEGMENTO", nullable = false)
	private Segmento segmento;
	
	@Column(name = "TIP_PERMISSAO", nullable = false)
	@Enumerated(EnumType.STRING)
	private TipoPermissao tipoPermissao;
	
	public Permissao(final Long sequencial, final Segmento segmento, final TipoPermissao tipoPermissao) {
		Validate.notNull(sequencial, "permissao.sequencial.required");
		Validate.notNull(segmento, "permissao.segmento.required");
		Validate.notNull(tipoPermissao, "permissao.tipoPermissao.required");
		
		this.sequencial = sequencial;
		this.segmento = segmento;
		this.tipoPermissao = tipoPermissao;
	}
	
	public Long toLong() {
		return sequencial;
	}
	
	public Segmento segmento() {
		return segmento;
	}
	
	public TipoPermissao tipoPermissao() {
		return tipoPermissao;
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
	
		Permissao other = (Permissao) obj;
		return sameValueAs(other);
	}
	
	@Override
	public boolean sameValueAs(Permissao other) {
		return other != null && sequencial.equals(other.sequencial);
	}

}
