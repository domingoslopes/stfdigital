package br.jus.stf.plataforma.acessos.domain.model;

import br.jus.stf.shared.stereotype.ValueObject;

public enum TipoPermissao implements ValueObject<TipoPermissao>{
	
	ALTERAR("Alterar"),
	CRIAR("Criar"),
	EXCLUIR("Excluir"),
	EXECUTAR("Executar"),
	IMPRIMIR("Imprimir"),
	PESQUISAR("Pesquisar"),
	VISUALIZAR("Visualizar");
	
	private String descricao;
	
	private TipoPermissao(final String descricao) {
		this.descricao = descricao;
	}
	
	public String descricao() {
		return descricao;
	}

	@Override
	public boolean sameValueAs(final TipoPermissao other) {
		return this.equals(other);
	}

}
