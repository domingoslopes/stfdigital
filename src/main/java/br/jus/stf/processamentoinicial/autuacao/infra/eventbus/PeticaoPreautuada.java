package br.jus.stf.processamentoinicial.autuacao.infra.eventbus;

import br.jus.stf.processamentoinicial.autuacao.domain.model.Peticao;

/**
 * Evento de petição preautuada.
 * 
 * @author Tomas.Godoi
 *
 */
public class PeticaoPreautuada {

	private Peticao peticao;
	
	public PeticaoPreautuada(Peticao peticao) {
		this.peticao = peticao;
	}
	
	public Peticao peticao() {
		return this.peticao;
	}
	
}
