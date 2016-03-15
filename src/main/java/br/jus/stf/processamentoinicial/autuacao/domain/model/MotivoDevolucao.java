package br.jus.stf.processamentoinicial.autuacao.domain.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.jus.stf.shared.TipoDocumentoId;
import br.jus.stf.shared.stereotype.ValueObject;

@Entity
@Table(name = "MOTIVO_DEVOLUCAO", schema = "AUTUACAO", uniqueConstraints = @UniqueConstraint(columnNames = {"DSC_MOTIVO_DEVOLUCAO"}))
public class MotivoDevolucao implements ValueObject<MotivoDevolucao> {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "SEQ_MOTIVO_DEVOLUCAO")
	private Long sequencial;
	
	@Column(name = "DSC_MOTIVO_DEVOLUCAO", nullable = false)
	private String descricao;
	
	@ElementCollection
	@CollectionTable(name = "MOTIVO_DEVOLUCAO_TIPO_MODELO", schema = "AUTUACAO",
		joinColumns = @JoinColumn(name = "SEQ_MOTIVO_DEVOLUCAO", nullable = false))
	private Set<TipoDocumentoId> tiposModelo = new HashSet<TipoDocumentoId>(0);
	
	
	MotivoDevolucao() {
		
	}
	
	public MotivoDevolucao(final Long sequencial, final String descricao) {
		Validate.notNull(sequencial, "motivoDevolucao.sequencial.required");
		Validate.notBlank(descricao, "motivoDevolucao.descricao.required");
		
		this.sequencial = sequencial;
		this.descricao = descricao;
	}
	
	public Long toLong() {
		return sequencial;
	}
	
	public String descricao() {
		return descricao;
	}
	
	public Set<TipoDocumentoId> tiposModelo(){
		return Collections.unmodifiableSet(tiposModelo);
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
	
		MotivoDevolucao other = (MotivoDevolucao) obj;
		
		return sameValueAs(other);
	}

	@Override
	public boolean sameValueAs(final MotivoDevolucao other) {
		return other != null
				&& sequencial.equals(other.sequencial);
	}

}
