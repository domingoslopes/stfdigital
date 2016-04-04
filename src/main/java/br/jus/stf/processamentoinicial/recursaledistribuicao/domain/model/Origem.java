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
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Type;

import br.jus.stf.processamentoinicial.suporte.domain.model.Peca;
import br.jus.stf.processamentoinicial.suporte.domain.model.TribunalJuizo;
import br.jus.stf.processamentoinicial.suporte.domain.model.UnidadeFederacao;
import br.jus.stf.shared.stereotype.ValueObject;

@Entity
@Table(name = "PROCESSO_ORIGEM", schema = "AUTUACAO",
	uniqueConstraints = @UniqueConstraint(columnNames = {"SEQ_UNIDADE_FEDERACAO", "COD_TRIBUNAL_JUIZO", "NUM_PROCESSO"}))
public class Origem implements ValueObject<Origem> {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SEQ_PROCESSO_ORIGEM")
	@SequenceGenerator(name = "PROCESSOORIGEMID", sequenceName = "AUTUACAO.SEQ_PROCESSO_ORIGEM", allocationSize = 1)
	@GeneratedValue(generator = "PROCESSOORIGEMID", strategy = GenerationType.SEQUENCE)
	private Long sequencial;
	
	@ManyToOne
	@JoinColumn(name = "SEQ_UNIDADE_FEDERACAO")
	private UnidadeFederacao procedenciaGeografica;
	
	@ManyToOne
	@JoinColumn(name = "COD_TRIBUNAL_JUIZO")
	private TribunalJuizo tribunalJuizo;
	
	@Column(name = "NUM_PROCESSO")
	private Long numeroProcesso;
	
	@Column(name = "FLG_PRINCIPAL")
	@Type(type = "yes_no")
	private boolean origemPrincipal;
	
	Origem() {
		
	}
	
	public Origem(final UnidadeFederacao procedenciaGeografica, final TribunalJuizo tribunalJuizo,
			final Long numeroProcesso, final boolean origemPrincipal) {
		Validate.notNull(procedenciaGeografica, "origem.procedenciaGeografica.required");
		Validate.notNull(tribunalJuizo, "origem.tribunalJuizo.required");
		Validate.notNull(numeroProcesso, "origem.numeroProcesso.required");
		
		this.procedenciaGeografica = procedenciaGeografica;
		this.tribunalJuizo = tribunalJuizo;
		this.numeroProcesso = numeroProcesso;
		this.origemPrincipal = origemPrincipal;
	}
	
	public Long toLong() {
		return sequencial;
	}
	
	public UnidadeFederacao procedenciaGeografica() {
		return procedenciaGeografica;
	}
	
	public TribunalJuizo tribunalJuizo() {
		return tribunalJuizo;
	}
	
	public Long numeroProcesso() {
		return numeroProcesso;
	}
	
	public boolean origemPrincipal() {
		return origemPrincipal;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(sequencial).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
	    if (this == obj) {
	    	return true;
	    }
	    
	    if (obj == null || !(obj instanceof Peca)) {
	    	return false;
	    }
	    
	    final Origem other = (Origem) obj;
	    return sameValueAs(other);
	}
	
	@Override
	public boolean sameValueAs(final Origem other){
		return other != null && sequencial.equals(other.sequencial);
	}

}
