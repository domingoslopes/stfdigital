package br.jus.stf.processamentoinicial.distribuicao.domain.model;

import br.jus.stf.shared.stereotype.ValueObject;

public enum TipoDistribuicao implements ValueObject<TipoDistribuicao> {
	
	COMUM,
	PREVENCAO;
	
	@Override
	public String toString() {
		String forma = null;
		
		switch(this) {
			case COMUM: forma = "Comum"; break;
			case PREVENCAO:	forma = "Prevenção Relator/Sucessor"; break;
			default: break;
		}
		return forma;
	}
	
	@Override
	public boolean sameValueAs(final TipoDistribuicao other) {
		return this.equals(other);
	}

}
