package br.jus.stf.jurisprudencia.controletese.domain.model;

import br.jus.stf.shared.stereotype.ValueObject;

public enum TipoTese implements ValueObject<TipoTese> {
	
	CONTROVERSIA("Controversia"),
	PRE_TEMA("Pré-tema"),
	REPERCUSSAO_GERAL("Repercussão Geral");
	
	private String descricao;
	
	private TipoTese(final String descricao) {
		this.descricao = descricao;
	}
	
	public String descricao() {
		return descricao;
	}

	@Override
	public boolean sameValueAs(final TipoTese other) {
		return this.equals(other);
	}

}
