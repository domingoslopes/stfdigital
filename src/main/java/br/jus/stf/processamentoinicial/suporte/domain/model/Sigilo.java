package br.jus.stf.processamentoinicial.suporte.domain.model;

import br.jus.stf.shared.stereotype.ValueObject;

/**
 * @author Rafael.Alencar
 *
 */
public enum Sigilo implements ValueObject<Sigilo> {
	
	PUBLICO("Público"),
	SEGREDO_JUSTICA("Segredo de Justiça");
	
	private String descricao;
	
	private Sigilo(final String descricao) {
		this.descricao = descricao;
	}
	
	public String descricao() {
		return descricao;
	}

    public boolean sameValueAs(final Sigilo other) {
	    return this.equals(other);
    }

}
