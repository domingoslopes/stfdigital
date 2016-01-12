package br.jus.stf.processamentoinicial.suporte.domain.model;

import br.jus.stf.shared.stereotype.ValueObject;

public enum TipoProcesso implements ValueObject<TipoProcesso> {
	
	ORIGINARIO("Originário"),
	RECURSAL("Recursal");
	
	private String descricao;
	
	private TipoProcesso(final String descricao) {
		this.descricao = descricao;
	}
	
	public String descricao() {
		return descricao;
	}
	
	@Override
	public boolean sameValueAs(final TipoProcesso other) {
		return this.equals(other);
	}

}
