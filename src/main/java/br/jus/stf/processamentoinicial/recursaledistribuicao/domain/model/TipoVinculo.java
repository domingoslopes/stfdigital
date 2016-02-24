package br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model;

import br.jus.stf.shared.stereotype.ValueObject;



public enum TipoVinculo implements ValueObject<TipoVinculo> {
	
	DIVISAO("Divisão"),
	UNIAO("União");
	
	private String descricao;
	
	private TipoVinculo(final String descricao) {
		this.descricao = descricao;
	}
	
	public String descricao() {
		return descricao;
	}

	@Override
	public boolean sameValueAs(final TipoVinculo other) {
		return this.equals(other);
	}
}
