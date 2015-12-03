package br.jus.stf.processamentoinicial.distribuicao.domain.model;

import br.jus.stf.shared.stereotype.ValueObject;

/**
 * @author Rafael Alencar
 *
 */
public enum ProcessoSituacao implements ValueObject<ProcessoSituacao> {
	
	DISTRIBUIDO("Distribuído");
	
	private String descricao;

	private ProcessoSituacao(final String descricao) {
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
	public boolean sameValueAs(final ProcessoSituacao other) {
		return this.equals(other);
	}
	
}
