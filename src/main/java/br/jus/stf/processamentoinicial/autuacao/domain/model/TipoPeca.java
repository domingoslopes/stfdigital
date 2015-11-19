package br.jus.stf.processamentoinicial.autuacao.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.stereotype.ValueObject;

@Entity
@Table(name = "TIPO_PECA", schema = "AUTUACAO")
public class TipoPeca implements ValueObject<TipoPeca> {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SEQ_TIPO_PECA")
	@SequenceGenerator(name = "TIPOPECAID", sequenceName = "AUTUACAO.SEQ_TIPO_PECA", allocationSize = 1)
	@GeneratedValue(generator = "TIPOPECAID", strategy=GenerationType.SEQUENCE)
	private Long sequencial;
	
	@Column(name = "NOM_TIPO_PECA", nullable = false)
	private String nome;
	
	TipoPeca() {

	}
	
	public TipoPeca(final Long sequencial, final String nome) {
		Validate.notNull(sequencial, "tipoPeca.sequencial.required");
		Validate.notBlank(nome, "tipoPeca.nome.required");
		
		this.sequencial = sequencial;
		this.nome = nome;
	}
	
	public Long toLong() {
		return this.sequencial;
	}
	
	public String nome() {
		return this.nome;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sequencial == null) ? 0 : sequencial.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || !(obj instanceof TipoPeca)) return false;
	    
	    final TipoPeca other = (TipoPeca) obj;
	    return sameValueAs(other);
	}
	
	@Override
	public boolean sameValueAs(final TipoPeca other){
		return other != null && this.sequencial.equals(other.sequencial);
	}
	
}
