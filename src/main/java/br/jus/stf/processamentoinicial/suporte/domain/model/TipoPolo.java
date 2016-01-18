package br.jus.stf.processamentoinicial.suporte.domain.model;

import br.jus.stf.shared.stereotype.ValueObject;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:57:03
 */
public enum TipoPolo implements ValueObject<TipoPolo> {
	POLO_ATIVO("Polo ativo"),
	POLO_PASSIVO("Polo passivo");
	
	private String descricao;
	
	private TipoPolo(final String descricao) {
		this.descricao = descricao;
	}
	
	public String descricao() {
		return descricao;
	}

	/**
	 * 
	 * @param other
	 */
	@Override
	public boolean sameValueAs(final TipoPolo other) {
		return this.equals(other);
	}
}