package br.jus.stf.processamentoinicial.autuacao.infra.eventbus;

import br.jus.stf.processamentoinicial.autuacao.domain.model.Peticao;

/**
 * Evento usado para notificar o gestor da autuação da rejeição de uma petição.
 * 
 * @author Anderson.Araujo
 *
 */
public class PeticaoRejeitada {
	
	private Peticao peticao;
	
	public PeticaoRejeitada(Peticao peticao) {
		this.peticao = peticao;
	}
	
	public Peticao peticao() {
		return this.peticao;
	}
}
