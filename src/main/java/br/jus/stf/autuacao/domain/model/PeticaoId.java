package br.jus.stf.autuacao.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import br.jus.stf.shared.domain.stereotype.ValueObject;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:33:25
 */
@Embeddable
public class PeticaoId implements ValueObject<PeticaoId> {

	private static final long serialVersionUID = 1L;
		
	@Column(name = "NUM_PETICAO", insertable = true, updatable = false, nullable = false)
	private Long numero;
	
	@Column(name = "NUM_ANO_PETICAO", insertable = true, updatable = false, nullable = false)
	private Short ano;

	public PeticaoId(final Long numero, final Short ano){
		this.numero = numero;
		this.ano = ano;
	}

	public Long numero(){
		return numero;
	}
	
	public Short ano() {
		return ano;
	}

	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + ((ano == null) ? 0 : ano.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(final Object o){
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
	
		PeticaoId other = (PeticaoId) o;
		return sameValueAs(other);
	}

	/**
	 * 
	 * @param other
	 */
	public boolean sameValueAs(final PeticaoId other){
		return other != null && this.numero.equals(other.numero) 
				&& this.ano.equals(other.ano);
	}

	public String toString(){
		return numero.toString();
	}

}