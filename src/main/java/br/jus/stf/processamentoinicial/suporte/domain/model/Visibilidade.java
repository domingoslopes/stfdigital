package br.jus.stf.processamentoinicial.suporte.domain.model;

import br.jus.stf.shared.stereotype.ValueObject;


public enum Visibilidade implements ValueObject<Visibilidade> {
	
	PUBLICO("Público"),
	PENDENTE_VISUALIZACAO("Pendente de visualização");
	
	private String descricao;
	
	private Visibilidade(final String descricao) {
		this.descricao = descricao;
	}
	
	public String descricao() {
		return descricao;
	}

    public boolean sameValueAs(final Visibilidade other) {
	    return this.equals(other);
    }
	
}
