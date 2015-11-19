package br.jus.stf.plataforma.notificacoes.infra.eventbus;

import static reactor.bus.selector.Selectors.$;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.fn.Consumer;
import br.jus.stf.plataforma.notificacoes.domain.model.Notificacao;
import br.jus.stf.plataforma.notificacoes.domain.model.NotificacaoService;

/**
 * Consumidor de eventos de aplicação para enviar para indexação
 * 
 * @author Lucas.Rodrigues
 *
 */
@Component
public class NotificacaoEnviadaConsumer implements Consumer<Event<NotificacaoEnviada>>, InitializingBean {
	
	@Autowired
	private EventBus eventBus;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		eventBus.on($("indexadorEventBus"), this);
	}
	
	@Override
	public void accept(Event<NotificacaoEnviada> event) {
		Notificacao notificacao = event.getData().notificacao();
		try {
			NotificacaoService notificacaoService = applicationContext.getBean(notificacao.tipo().strategy());
			notificacaoService.emitir(notificacao);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
