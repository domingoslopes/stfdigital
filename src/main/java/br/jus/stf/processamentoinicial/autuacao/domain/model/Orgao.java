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

/**
 * @author Rafael Alencar
 */
@Entity
@Table(name = "ORGAO", schema = "AUTUACAO")
public class Orgao implements ValueObject<Orgao> {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SEQ_ORGAO")
	@SequenceGenerator(name = "ORGAOID", sequenceName = "AUTUACAO.SEQ_ORGAO", allocationSize = 1)
	@GeneratedValue(generator = "ORGAOID", strategy=GenerationType.SEQUENCE)
	private Long sequencial;
	
	@Column(name = "NOM_ORGAO", nullable = false)
	private String nome;
	
	Orgao() {

	}
	
	public Orgao(final Long sequencial, final String nome) {
		Validate.notNull(sequencial, "orgao.sequencial.required");
		Validate.notBlank(nome, "orgao.nome.required");
		
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
	    if (obj == null || !(obj instanceof Orgao)) return false;
	    
	    final Orgao other = (Orgao) obj;
	    return sameValueAs(other);
	}
	
	@Override
	public boolean sameValueAs(final Orgao other){
		return other != null && this.sequencial.equals(other.sequencial);
	}
	
}
