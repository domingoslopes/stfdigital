package br.jus.stf.plataforma.notificacoes.infra.eventbus;

import static reactor.bus.selector.Selectors.$;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.fn.Consumer;
import br.jus.stf.plataforma.notificacoes.domain.model.Notificacao;
import br.jus.stf.plataforma.notificacoes.domain.model.NotificacaoId;
import br.jus.stf.plataforma.notificacoes.infra.configuration.NotificacoesConfiguration;
import br.jus.stf.plataforma.shared.indexacao.IndexadorRestAdapter;

/**
 * Consumidor de eventos de aplicação para enviar para indexação
 * 
 * @author Lucas.Rodrigues
 *
 */
@Component
public class NotificacaoLidaConsumer implements Consumer<Event<NotificacaoLida>>, InitializingBean {
	
	@Autowired
	private EventBus eventBus;
	
	@Autowired
	private IndexadorRestAdapter indexadorRestAdapter;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		eventBus.on($("indexadorEventBus"), this);
	}
	
	@Override
	public void accept(Event<NotificacaoLida> event) {
		NotificacaoId id = event.getData().id();
		try {
			Map<String, Object> mapa = montarMapaDeAtualizacao();
			String tipo = Notificacao.class.getSimpleName();
			indexadorRestAdapter.atualizar(NotificacoesConfiguration.INDICE, id.toString(), tipo, mapa);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Map<String, Object> montarMapaDeAtualizacao() {
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("lida", true);
		return mapa;
	}

}
