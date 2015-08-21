package br.jus.stf.autuacao.domain.model;

import br.jus.stf.shared.domain.stereotype.ValueObject;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:56:20
 */
public enum PeticaoStatus implements ValueObject<PeticaoStatus> {
	A_AUTUAR,
	EM_AUTUACAO,
	ACEITA,
	RECUSADA,
	DISTRIBUIDA;

	/**
	 * 
	 * @param other
	 */
	public boolean sameValueAs(final PeticaoStatus other){
		return this.equals(other);
	}
}