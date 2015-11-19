package br.jus.stf.plataforma.acessos.domain.model;

import br.jus.stf.shared.stereotype.ValueObject;

public enum TipoPermissao implements ValueObject<TipoPermissao>{
	
	CRIAR("Criar"),
	ALTERAR("Alterar"),
	EXCLUIR("Excluir"),
	PESQUISAR("Pesquisar"),
	VISUALIZAR("Visualiazar"),
	IMPRIMIR("Imprimir"),
	EXECUTAR("Executar");
	
	private String descricao;
	
	private TipoPermissao(final String descricao) {
		this.descricao = descricao;
	}
	
	public String descricao() {
		return descricao;
	}
	
	@Override
	public String toString() {
		return descricao;
	}

	@Override
	public boolean sameValueAs(final TipoPermissao other) {
		return this.equals(other);
	}

}
