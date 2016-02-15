package br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model;

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
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.jus.stf.shared.stereotype.ValueObject;

@Entity
@Table(name = "PROCESSO_MOTIVO_INAPTIDAO", schema = "AUTUACAO")
public class MotivoInaptidaoProcesso implements ValueObject<MotivoInaptidaoProcesso> {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "SEQ_PROCESSO_MOTIVO_INAPTIDAO")
	@SequenceGenerator(name = "PROCESSOMOTIVOINAPTIDAOID", sequenceName = "AUTUACAO.SEQ_PROCESSO_MOTIVO_INAPTIDAO", allocationSize = 1)
	@GeneratedValue(generator = "PROCESSOMOTIVOINAPTIDAOID", strategy = GenerationType.SEQUENCE)
	private Long sequencial;
	
	@ManyToOne
	@JoinColumn(name = "COD_MOTIVO_INAPTIDAO", referencedColumnName = "COD_MOTIVO_INAPTIDAO")
	private MotivoInaptidao motivoInaptidao;
	
	@Column(name = "DSC_MOTIVO_INAPTIDAO")
	private String observacao;
	
	MotivoInaptidaoProcesso() {
		
	}
	
	public MotivoInaptidaoProcesso(final MotivoInaptidao motivoInaptidao, final String observacao) {
		Validate.notNull(motivoInaptidao, "motivoInaptidaoProcesso.motivoInaptidao.required");

		this.motivoInaptidao = motivoInaptidao;
		this.observacao = observacao;
	}
	
	public Long toLong() {
		return this.sequencial;
	}
	
	public MotivoInaptidao motivoInaptidao() {
		return motivoInaptidao;
	}
	
	public String observacao() {
		return observacao;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(motivoInaptidao).hashCode();
	}
	
	@Override
	public boolean equals(Object obj){
		
		if (this == obj) {
			return true;
		}
		
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
	
		MotivoInaptidaoProcesso other = (MotivoInaptidaoProcesso) obj;
		
		return motivoInaptidao.equals(other.motivoInaptidao);
	}

	@Override
	public boolean sameValueAs(final MotivoInaptidaoProcesso other) {
		return other != null
				&& motivoInaptidao.equals(other.motivoInaptidao);
	}
	
}
