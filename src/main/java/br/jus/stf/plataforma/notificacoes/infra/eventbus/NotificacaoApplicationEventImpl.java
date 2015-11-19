package br.jus.stf.plataforma.notificacoes.infra.eventbus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.bus.Event;
import reactor.bus.EventBus;
import br.jus.stf.plataforma.notificacoes.application.NotificacaoApplicationEvent;
import br.jus.stf.plataforma.notificacoes.domain.model.Notificacao;
import br.jus.stf.plataforma.notificacoes.domain.model.NotificacaoId;

/**
 * Classe que implementa a publicação de eventos de petição
 * 
 * @author Lucas.Rodrigues
 *
 */
@Component
public class NotificacaoApplicationEventImpl implements NotificacaoApplicationEvent {

	@Autowired
	private EventBus eventBus;
	
	@Override
	public void notificacaoEnviada(Notificacao notificacao) {
		eventBus.notify("indexadorEventBus", Event.wrap(new NotificacaoEnviada(notificacao)));
	}

	@Override
	public void notificacaoLida(NotificacaoId id) {
		eventBus.notify("indexadorEventBus", Event.wrap(new NotificacaoLida(id)));
	}

}
