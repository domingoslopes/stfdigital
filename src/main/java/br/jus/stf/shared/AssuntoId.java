package br.jus.stf.shared;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.jus.stf.shared.stereotype.ValueObject;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 08-jan-2016
 */
@Embeddable
public class AssuntoId implements ValueObject<AssuntoId>{

	private static final long serialVersionUID = 1L;
	
	@Column(name = "COD_ASSUNTO", nullable = false)
	private String codigo;

	AssuntoId() {

	}

	public AssuntoId(final String codigo){
		Validate.notBlank(codigo, "assuntoId.codigo.required");
		
		this.codigo = codigo;
	}

	@Override
	public String toString(){
		return codigo;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(codigo).toHashCode();
	}
	
	@Override
	public boolean equals(final Object o){
		
		if (this == o) {
			return true;
		}
		
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		
		AssuntoId other = (AssuntoId) o;
		
		return sameValueAs(other);
	}

	@Override
	public boolean sameValueAs(final AssuntoId other){
		return other != null && this.codigo.equals(other.codigo);
	}
	
}