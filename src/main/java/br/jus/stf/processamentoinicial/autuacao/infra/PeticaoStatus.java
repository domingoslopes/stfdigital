package br.jus.stf.processamentoinicial.autuacao.infra;


/**
 * @author Lucas.Rodrigues
 *
 */
public enum PeticaoStatus {

	A_PREAUTUAR("A preautuar"),
	A_AUTUAR("A autuar"),
	ACEITA("Aceita"),
	REJEITADA("Rejeitada"),
	DISTRIBUIDA("Distribuída"),
	A_DEVOLVER("A devolver"),
	ASSINAR_DEVOLUCAO("Assinar devolução"),
	DEVOLVIDA("Devolvida");
	
	private String descricao;

	private PeticaoStatus(final String descricao) {
		this.descricao = descricao;
	}

	public String descricao() {
		return descricao;
	}

	@Override
	public String toString() {
		return descricao;
	}
	
}
