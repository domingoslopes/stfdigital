package br.jus.stf.plataforma.notificacoes.infra.eventbus;

import br.jus.stf.plataforma.notificacoes.domain.model.NotificacaoId;

/**
 * @author Lucas.Rodrigues
 *
 */
public class NotificacaoLida {
	
	private NotificacaoId id;
	
	public NotificacaoLida(NotificacaoId id) {
		this.id = id;
	}
	
	public NotificacaoId id() {
		return id;
	}

}
