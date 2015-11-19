package br.jus.stf.processamentoinicial.distribuicao.domain.model;

import br.jus.stf.shared.stereotype.ValueObject;

public enum TipoDistribuicao implements ValueObject<TipoDistribuicao> {
	
	COMUM,
	PREVENCAO;
	
	@Override
	public String toString() {
		String tipo = null;
		
		switch(this) {
			case COMUM: tipo = "Comum"; break;
			case PREVENCAO:	tipo = "Prevenção"; break;
			default: break;
		}
		return tipo;
	}
	
	@Override
	public boolean sameValueAs(final TipoDistribuicao other) {
		return this.equals(other);
	}

}
