package br.jus.stf.processamentoinicial.distribuicao.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.jus.stf.shared.stereotype.ValueObject;

@Entity
@Table(name = "AUTUACAO", schema = "MOTIVO_INAPTIDAO", uniqueConstraints = @UniqueConstraint(columnNames = {"DSC_MOTIVO_INAPTIDAO"}))
public class MotivoInaptidao implements ValueObject<MotivoInaptidao> {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "SEQ_MOTIVO_INAPTIDAO")
	@SequenceGenerator(name = "MOTIVOINAPTIDAOID", sequenceName = "AUTUACAO.SEQ_MOTIVO_INAPTIDAO", allocationSize = 1)
	@GeneratedValue(generator = "MOTIVOINAPTIDAOID", strategy=GenerationType.SEQUENCE)
	private Long sequencial;
	
	@Column(name = "DSC_MOTIVO_INAPTIDAO", nullable = false)
	private String descricao;
	
	MotivoInaptidao() {
		
	}
	
	public MotivoInaptidao(final Long sequencial, final String descricao) {
		Validate.notNull(sequencial, "motivoInaptidao.sequencial.required");
		Validate.notBlank(descricao, "motivoInaptidao.descricao.required");
		
		this.sequencial = sequencial;
		this.descricao = descricao;
	}
	
	public Long toLong() {
		return this.sequencial;
	}
	
	public String descricao() {
		return descricao;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(sequencial).hashCode();
	}
	
	@Override
	public boolean equals(Object obj){
		
		if (this == obj) {
			return true;
		}
		
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
	
		MotivoInaptidao other = (MotivoInaptidao) obj;
		
		return sequencial.equals(other.sequencial);
	}

	@Override
	public boolean sameValueAs(final MotivoInaptidao other) {
		return other != null
				&& descricao.equals(other.descricao);
	}

}
