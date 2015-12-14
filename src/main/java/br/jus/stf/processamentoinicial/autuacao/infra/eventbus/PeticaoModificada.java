package br.jus.stf.processamentoinicial.autuacao.infra.eventbus;

import br.jus.stf.processamentoinicial.autuacao.domain.model.Peticao;

/**
 * Evento emitido quando uma petição for modificada ou uma nova for incluída.
 * 
 * @author Tomas.Godoi
 *
 */
public class PeticaoModificada {

	private Peticao peticao;

	public PeticaoModificada(Peticao peticao) {
		this.peticao = peticao;
	}

	public Peticao peticao() {
		return peticao;
	}

}
