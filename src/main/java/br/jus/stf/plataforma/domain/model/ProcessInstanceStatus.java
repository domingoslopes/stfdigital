package br.jus.stf.plataforma.domain.model;

import br.jus.stf.shared.domain.stereotype.ValueObject;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 19:33:25
 */
public enum ProcessInstanceStatus implements ValueObject<ProcessInstanceStatus> {
	PRE_AUTUAR,
	ANALISAR,
	ANALISE,
	AUTUAR,
	FINALIZAR_ANALISE,
	ABORTAR_ANALISE,
	DEVOLVER,
	FINALIZAR_DEVOLUCAO,
	DISTRIBUIR,
	FINALIZAR_DISTRIBUICAO;

	/**
	 * 
	 * @param other
	 */
	public boolean sameValueAs(final ProcessInstanceStatus other){
		return this.equals(other);
	}
}