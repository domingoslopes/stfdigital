package br.jus.stf.processamentoinicial.autuacao.infra.eventbus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.bus.Event;
import reactor.bus.EventBus;
import br.jus.stf.processamentoinicial.autuacao.application.PeticaoApplicationEvent;
import br.jus.stf.processamentoinicial.autuacao.domain.model.Peticao;
import br.jus.stf.processamentoinicial.distribuicao.domain.model.Processo;

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
		notificar(Event.wrap(peticao));
	}

	@Override
	public void processoDistribuido(Processo processo) {
		notificar(Event.wrap(processo));
	}
	
	private void notificar(Event<?> evento) {
		eventBus.notify("indexadorEventBus", evento);
	}

}
