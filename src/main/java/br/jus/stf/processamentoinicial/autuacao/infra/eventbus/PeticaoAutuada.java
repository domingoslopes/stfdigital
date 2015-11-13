package br.jus.stf.processamentoinicial.autuacao.infra.eventbus;

import br.jus.stf.processamentoinicial.autuacao.domain.model.Peticao;

/**
 * Evento usado para notificar o gestor da autuação da autuação de uma petição.
 * 
 * @author Anderson Araújo
 * @since 09.11.2015
 *
 */
public class PeticaoAutuada {
	
	private Peticao peticao;
	
	public PeticaoAutuada(Peticao peticao) {
		this.peticao = peticao;
	}
	
	public Peticao peticao() {
		return this.peticao;
	}
}
