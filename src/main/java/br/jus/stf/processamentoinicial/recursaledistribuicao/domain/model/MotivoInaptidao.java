package br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
	@Column(name = "COD_MOTIVO_INAPTIDAO")
	private Long codigo;
	
	@Column(name = "DSC_MOTIVO_INAPTIDAO", nullable = false)
	private String descricao;
	
	MotivoInaptidao() {
		
	}
	
	public MotivoInaptidao(final Long codigo, final String descricao) {
		Validate.notNull(codigo, "motivoInaptidao.codigo.required");
		Validate.notBlank(descricao, "motivoInaptidao.descricao.required");
		
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public Long toLong() {
		return this.codigo;
	}
	
	public String descricao() {
		return descricao;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(codigo).hashCode();
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
		
		return codigo.equals(other.codigo);
	}

	@Override
	public boolean sameValueAs(final MotivoInaptidao other) {
		return other != null
				&& descricao.equals(other.descricao);
	}

}
