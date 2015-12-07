package br.jus.stf.plataforma.acessos.domain.model;

import br.jus.stf.shared.stereotype.ValueObject;

public enum TipoGrupo implements ValueObject<TipoGrupo> {
	
	COMISSAO("Comissão"),
	CONFIGURACAO("Configuração"),
	GRUPO_TRABALHO("Grupo de trabalho"),
	ORGAO_CONVENIADO("Órgão conveniado"),
	SETOR("Setor"),
	TRIBUNAL("Tribunal");
	
	private String descricao;
	
	private TipoGrupo(final String descricao) {
		this.descricao = descricao;
	}
	
	public String descricao() {
		return descricao;
	}
	
	@Override
	public boolean sameValueAs(final TipoGrupo other) {
		return this.equals(other);
	}

}
