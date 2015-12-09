package br.jus.stf.plataforma.acessos.domain.model;

import br.jus.stf.shared.stereotype.ValueObject;

public enum TipoRecurso implements ValueObject<TipoRecurso>{
	
	ACAO("Ação"),
	DASHLET("Dashlet"),
	NOTIFICACAO("Notificação"),
	PESQUISA("Pesquisa"),
	TAREFA("Tarefa");
	
	private String descricao;
	
	private TipoRecurso(final String descricao) {
		this.descricao = descricao;
	}
	
	public String descricao() {
		return descricao;
	}

	@Override
	public boolean sameValueAs(final TipoRecurso other) {
		return this.equals(other);
	}

}
