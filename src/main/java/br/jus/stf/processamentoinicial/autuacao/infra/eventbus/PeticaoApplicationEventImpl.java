package br.jus.stf.processamentoinicial.autuacao.infra.eventbus;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.bus.Event;
import reactor.bus.EventBus;
import br.jus.stf.processamentoinicial.autuacao.application.PeticaoApplicationEvent;
import br.jus.stf.processamentoinicial.autuacao.domain.model.Peticao;

/**
 * Classe que implementa a publicação de eventos de petição
 * 
 * @author Lucas.Rodrigues
 *
 */
@Component
public class PeticaoApplicationEventImpl implements PeticaoApplicationEvent {

	@Autowired
	private EventBus eventBus;
	
	@Override
	public void peticaoRecebida(Peticao peticao) {
		eventBus.notify("indexadorEventBus", Event.wrap(peticao));
		eventBus.notify("notificadorEventBus", Event.wrap(montaNotificacao(peticao)));
	}
	
	private Map<String, String> montaNotificacao(Peticao peticao) {
		Map<String, String> notificacao = new HashMap<String, String>();
		notificacao.put("mensagem", "Petição " + peticao.identificacao() + " recebida.");
		notificacao.put("notificado", peticao.isEletronica() ? "autuador" : "preautuador");
		return notificacao;
	}

}
