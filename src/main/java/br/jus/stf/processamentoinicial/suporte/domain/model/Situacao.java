package br.jus.stf.processamentoinicial.suporte.domain.model;

import br.jus.stf.shared.stereotype.ValueObject;


public enum Situacao implements ValueObject<Situacao> {
	
	EXCLUIDA("Excluída"),
	JUNTADA("Juntada"),
	PENDENTE_JUNTADA("Pendente de juntada");
	
	private String descricao;
	
	private Situacao(final String descricao) {
		this.descricao = descricao;
	}
	
	public String descricao() {
		return descricao;
	}

    public boolean sameValueAs(final Situacao other) {
	    return this.equals(other);
    }
	
}
