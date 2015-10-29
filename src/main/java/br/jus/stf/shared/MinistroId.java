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
public class MinistroId implements ValueObject<MinistroId>{

	private static final long serialVersionUID = 1L;
	
	@Column(name = "COD_MINISTRO", nullable = false)
	private Long codigo;

	MinistroId() {

	}

	public MinistroId(final Long codigo){
		Validate.notNull(codigo, "ministroId.codigo.required");
		
		this.codigo = codigo;
	}

	public Long toLong(){
		return codigo;
	}
	
	@Override
	public String toString(){
		return codigo.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(final Object o){
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
	
		MinistroId other = (MinistroId) o;
		return sameValueAs(other);
	}

	@Override
	public boolean sameValueAs(final MinistroId other){
		return other != null && this.codigo.equals(other.codigo);
	}
	
}