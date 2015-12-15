package br.jus.stf.plataforma.shared.security.resource;

import br.jus.stf.shared.stereotype.ValueObject;

public enum ResourceType implements ValueObject<ResourceType>{
	
	ACAO("Ação"),
	DASHLET("Dashlet"),
	DASHBOARD("Dashboard"),
	NOTIFICACAO("Notificação"),
	PESQUISA("Pesquisa"),
	TAREFA("Tarefa");
	
	private String descricao;
	
	private ResourceType(final String descricao) {
		this.descricao = descricao;
	}
	
	public String descricao() {
		return descricao;
	}

	@Override
	public boolean sameValueAs(final ResourceType other) {
		return this.equals(other);
	}

}
