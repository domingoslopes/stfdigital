package br.jus.stf.autuacao.domain.model;

import br.jus.stf.shared.domain.stereotype.ValueObject;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:57:03
 */
public enum TipoPolo implements ValueObject<TipoPolo>{
	POLO_ATIVO,
	POLO_PASSIVO;

	/**
	 * 
	 * @param other
	 */
	public boolean sameValueAs(final TipoPolo other){
		return this.equals(other);
	}
}