package br.jus.stf.plataforma.identidades.domain.model;

import br.jus.stf.shared.stereotype.ValueObject;

public enum TipoAssociado implements ValueObject<TipoAssociado> {
	
	ASSOCIADO("Associado"),
	GESTOR("Gestor"),
	REPRESENTANTE("Representante");
	
	private String descricao;
	
	private TipoAssociado(final String descricao) {
		this.descricao = descricao;
	}
	
	public String descricao() {
		return descricao;
	}

	@Override
	public boolean sameValueAs(final TipoAssociado other) {
		return this.equals(other);
	}

}
