package br.jus.stf.plataforma.notificacoes.domain.model;

import br.jus.stf.shared.stereotype.ValueObject;

/**
 * @author Lucas.Rodrigues
 *
 */
public enum TipoNotificacao implements ValueObject<TipoNotificacao> {

	EMAIL(NotificacaoEmailService.class), 
	UI(NotificacaoUIService.class);
	
	private Class<? extends NotificacaoService> clazz;
	
	private TipoNotificacao(Class<? extends NotificacaoService> clazz) {
		this.clazz = clazz;
	}
	
	public Class<? extends NotificacaoService> strategy() {
		return clazz;
	};

	@Override
	public boolean sameValueAs(final TipoNotificacao other) {
		return this.equals(other);
	}
	
}
