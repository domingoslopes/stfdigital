package br.jus.stf.processamentoinicial.autuacao.infra.eventbus;

import br.jus.stf.processamentoinicial.autuacao.domain.model.Peticao;

/**
 * @author Lucas.Rodrigues
 *
 */
public class PeticaoRecebida {

	public Peticao peticao;
	
	public PeticaoRecebida(Peticao peticao) {
		this.peticao = peticao;
	}
	
	public Peticao peticao() {
		return peticao;
	}
	
}
