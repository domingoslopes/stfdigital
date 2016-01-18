package br.jus.stf.processamentoinicial.suporte.domain.model;

import br.jus.stf.shared.stereotype.ValueObject;

public enum Classificacao implements ValueObject<Classificacao> {
	
	APTO("Apto"),
	INAPTO("Inapto");
	
	private String descricao;
	
	private Classificacao(final String descricao) {
		this.descricao = descricao;
	}
	
	public String descricao() {
		return descricao;
	}

	@Override
	public boolean sameValueAs(final Classificacao other) {
		return this.equals(other);
	}

}
