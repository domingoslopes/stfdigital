package br.jus.stf.shared.domain.model;

import br.jus.stf.shared.domain.stereotype.ValueObject;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:33:46
 */
public class PessoaId implements ValueObject<PessoaId> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;

	public PessoaId(final Long id){
		this.id = id;
	}

	public Long id(){
		return id;
	}

	/**
	 * 
	 * @param o
	 */
	public boolean equals(final Object o){
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
	
		PessoaId other = (PessoaId) o;
	
		return sameValueAs(other);
	}

	public int hashCode(){
		return id.hashCode();
	}

	/**
	 * 
	 * @param other
	 */
	public boolean sameValueAs(final PessoaId other){
		return other != null && this.id.equals(other.id);
	}

	public String toString(){
		return id.toString();
	}

}