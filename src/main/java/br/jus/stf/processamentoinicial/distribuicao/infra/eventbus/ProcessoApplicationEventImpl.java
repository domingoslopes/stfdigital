package br.jus.stf.processamentoinicial.distribuicao.infra.eventbus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.bus.Event;
import reactor.bus.EventBus;
import br.jus.stf.processamentoinicial.distribuicao.application.ProcessoApplicationEvent;
import br.jus.stf.processamentoinicial.distribuicao.domain.model.Processo;

/**
 * Classe que implementa a publicação de eventos de petição
 * 
 * @author Lucas.Rodrigues
 *
 */
@Component
public class ProcessoApplicationEventImpl implements ProcessoApplicationEvent {

	@Autowired
	private EventBus eventBus;

	@Override
	public void processoDistribuido(Processo processo) {
		eventBus.notify("indexadorEventBus", Event.wrap(processo));
	}

}
