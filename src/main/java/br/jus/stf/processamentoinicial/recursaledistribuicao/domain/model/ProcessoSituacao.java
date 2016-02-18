package br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model;

import br.jus.stf.shared.stereotype.ValueObject;

/**
 * @author Rafael Alencar
 *
 */
public enum ProcessoSituacao implements ValueObject<ProcessoSituacao> {
	
	A_ANALISAR("A analisar"),
	A_AUTUAR("A autuar"),
	A_REVISAR("A revisar"),
	AUTUADO("Autuado"),
	DISTRIBUIDO("Distribuído"),
	PECAS_ORGANIZADAS("Peças organizadas");
	
	private String descricao;
	
	private ProcessoSituacao(final String descricao) {
		this.descricao = descricao;
	}
	
	public String descricao() {
		return descricao;
	}

	@Override
	public boolean sameValueAs(final ProcessoSituacao other) {
		return this.equals(other);
	}
	
}
