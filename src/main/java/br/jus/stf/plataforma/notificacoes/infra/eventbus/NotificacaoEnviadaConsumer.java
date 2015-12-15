package br.jus.stf.plataforma.notificacoes.infra.eventbus;

import static reactor.bus.selector.Selectors.$;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.fn.Consumer;
import br.jus.stf.plataforma.notificacoes.domain.model.Notificacao;
import br.jus.stf.plataforma.notificacoes.domain.model.NotificacaoService;
import br.jus.stf.plataforma.notificacoes.domain.model.NotificacaoUIService;
import br.jus.stf.plataforma.notificacoes.domain.model.TipoNotificacao;
import br.jus.stf.plataforma.notificacoes.infra.NotificacaoServiceLocator;
import br.jus.stf.plataforma.shared.security.SecurityContextUtil;

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
	private NotificacaoUIService notificacaoUIService;
	
	@Autowired
	private NotificacaoServiceLocator notificacaoServiceLocator;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		eventBus.on($("indexadorEventBus"), this);
	}
	
	@Override
	public void accept(Event<NotificacaoEnviada> event) {
		List<Notificacao> notificacoes = event.getData().notificacoes();
		try {
			TipoNotificacao tipo = notificacoes.get(0).tipo();
			Optional<NotificacaoService> notificacaoService = notificacaoServiceLocator.getBean(tipo.strategy());
			if (notificacaoService.isPresent()) {
				for (Notificacao notificacao : notificacoes) {
					notificacaoService.get().emitir(notificacao);
				}
			} else {
				String msg = "Usuário sem permissões para emitir notificações de " + tipo.name();
				Notificacao notificacaoErro = new Notificacao(TipoNotificacao.UI, msg, SecurityContextUtil.getUsername());
				notificacaoUIService.emitir(notificacaoErro);
			}			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
