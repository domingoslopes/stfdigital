package br.jus.stf.processamentoinicial.autuacao.infra.eventbus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.bus.Event;
import reactor.bus.EventBus;
import br.jus.stf.processamentoinicial.autuacao.application.PeticaoApplicationEvent;
import br.jus.stf.processamentoinicial.autuacao.domain.model.Peticao;

/**
 * Classe que implementa a publicação de eventos de petição.
 * 
 * @author Lucas.Rodrigues
 *
 */
@Component
public class PeticaoApplicationEventImpl implements PeticaoApplicationEvent {

	@Autowired
	private EventBus eventBus;
	
	/**
	 * Notifica interessados sobre o recebimento de uma petição.
	 * 
	 * @param peticao Dados da petição.
	 */
	@Override
	public void peticaoRecebida(Peticao peticao) {
		PeticaoRecebida evento = new PeticaoRecebida(peticao);
		eventBus.notify("indexadorEventBus", Event.wrap(new PeticaoModificada(peticao)));
		eventBus.notify("notificadorEventBus", Event.wrap(evento));
	}
	
	/**
	 * Evento usado para notifica interessados sobre a remessa inválida de uma petição.
	 * 
	 * @param peticao Dados da petição.
	 */
	@Override
	public void remessaInvalida(Peticao peticao) {
		RemessaInvalida evento = new RemessaInvalida(peticao);
		eventBus.notify("notificadorEventBus", Event.wrap(evento));
	}
	
	/**
	 * Evento usado para notificar interessados sobre a rejeição de uma petição.
	 * 
	 * @param peticao Dados da petição.
	 */
	@Override
	public void peticaoRejeitada(Peticao peticao){
		PeticaoRejeitada evento = new PeticaoRejeitada(peticao);
		eventBus.notify("notificadorEventBus", Event.wrap(evento));
	}

	/**
	 * Evento utilizado para notificar o(s) interessado(s) acerda da autuação de uma petição.
	 * 
	 * @param peticao Dados da petição autuada.
	 */
	@Override
	public void peticaoAutuada(Peticao peticao) {
		PeticaoAutuada evento = new PeticaoAutuada(peticao);
		eventBus.notify("indicadorEventBus", Event.wrap(evento));
	}
	
	/**
	 * Evento utilizado para notificar o(s) interessado(s) acerda da preautuação de uma petição.
	 * 
	 * @param peticao Dados da petição preautuada.
	 */
	@Override
	public void peticaoPreautuada(Peticao peticao) {
		PeticaoPreautuada evento = new PeticaoPreautuada(peticao);
		eventBus.notify("indicadorEventBus", Event.wrap(evento));
	}

	/**
	 * Notifica interessados sobre a preparação da petição para devolução.
	 * 
	 * @param peticao
	 */
	@Override
	public void peticaoPreparadaParaDevolucao(Peticao peticao) {
		eventBus.notify("indexadorEventBus", Event.wrap(new PeticaoModificada(peticao)));
	}

	/**
	 * Notifica interessados sobre a assinatura do documento de devolução.
	 * 
	 */
	@Override
	public void peticaoDevolucaoAssinada(Peticao peticao) {
		eventBus.notify("indexadorEventBus", Event.wrap(new PeticaoModificada(peticao)));
	}

}
