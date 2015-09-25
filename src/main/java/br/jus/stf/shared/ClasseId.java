package br.jus.stf.shared;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.stereotype.ValueObject;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:33:46
 */
@Embeddable
public class ClasseId implements ValueObject<ClasseId> {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "SIG_CLASSE", nullable = false)
	private String sigla;

	public ClasseId(final String sigla){
		Validate.notBlank(sigla, "classeId.sigla.required");
		
		this.sigla = sigla;
	}

	@Override
	public String toString(){
		return sigla;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sigla == null) ? 0 : sigla.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(final Object o){
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
	
		ClasseId other = (ClasseId) o;
		return sameValueAs(other);
	}

	/**
	 * 
	 * @param other
	 */
	public boolean sameValueAs(final ClasseId other){
		return other != null && this.sigla.equals(other.sigla);
	}

	//Hibernate
	
	ClasseId() {
		
	}
	
}