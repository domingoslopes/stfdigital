package br.jus.stf.processamentoinicial.suporte.domain.model;

import br.jus.stf.shared.stereotype.ValueObject;

public enum MeioTramitacao implements ValueObject<MeioTramitacao> {
	
	ELETRONICO("Eletrônico"),
	FISICO("Físico");
	
	private String descricao;
	
	private MeioTramitacao(final String descricao) {
		this.descricao = descricao;
	}
	
	public String descricao() {
		return descricao;
	}

    public boolean sameValueAs(final MeioTramitacao other) {
	    return this.equals(other);
    }

}
