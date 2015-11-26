package br.jus.stf.processamentoinicial.distribuicao.domain.model;

import br.jus.stf.shared.stereotype.ValueObject;

public enum TipoDistribuicao implements ValueObject<TipoDistribuicao> {
	
	COMUM("Comum"),
	PREVENCAO("Prevenção");
	
	private String descricao;
	
	private TipoDistribuicao(final String descricao) {
		this.descricao = descricao;
	}
	
	public String descricao() {
		return descricao;
	}
	
	@Override
	public String toString() {
		return descricao;
	}

	@Override
	public boolean sameValueAs(final TipoDistribuicao other) {
		return this.equals(other);
	}

}
