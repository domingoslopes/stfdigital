package br.jus.stf.processamentoinicial.autuacao.infra.eventbus;

import br.jus.stf.processamentoinicial.autuacao.domain.model.Peticao;

/**
 * Evento usado para notificar usuários sobre a remessa inválida de uma petição.
 * 
 * @author Anderson.Araujo
 *
 */
public class RemessaInvalida {
	
	private Peticao peticao;
	
	public RemessaInvalida(Peticao peticao) {
		this.peticao = peticao;
	}
	
	public Peticao peticao() {
		return this.peticao;
	}
}
