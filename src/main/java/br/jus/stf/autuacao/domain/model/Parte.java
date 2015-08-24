package br.jus.stf.autuacao.domain.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.jus.stf.shared.domain.model.PessoaId;
import br.jus.stf.shared.domain.stereotype.ValueObject;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:33:25
 */
@Entity
@Table(name = "PARTE")
@SequenceGenerator(name = "PARTEID", sequenceName = "SEQ_PARTE", allocationSize = '1')
public class Parte implements ValueObject<Parte>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Embedded
	private PessoaId pessoaId;
	
	@Enumerated(EnumType.STRING)
	private TipoPolo polo;

	public Parte(final PessoaId pessoaId, final TipoPolo polo){
		this.pessoaId = pessoaId;
		this.polo = polo;
	}

	public PessoaId pessoaId(){
		return this.pessoaId;
	}

	public TipoPolo polo(){
		return this.polo;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pessoaId == null) ? 0 : pessoaId.hashCode());
		result = prime * result + ((polo == null) ? 0 : polo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    
	    final Parte other = (Parte) obj;
	    return sameValueAs(other);
	}

	/**
	 * 
	 * @param other
	 */
	public boolean sameValueAs(final Parte other){
		return other != null && this.pessoaId.sameValueAs(other.pessoaId) && this.polo.sameValueAs(other.polo);
	}
	
	//Hibernate
	@Id
	@GeneratedValue(generator = "PARTEID", strategy=GenerationType.SEQUENCE)
	@Column(name = "SEQ_PARTE")
	private Long id;

	Parte() {
		
	}
}