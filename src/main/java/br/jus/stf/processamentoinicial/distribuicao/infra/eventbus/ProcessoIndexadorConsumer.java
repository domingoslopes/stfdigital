package br.jus.stf.processamentoinicial.distribuicao.infra.eventbus;

import static reactor.bus.selector.Selectors.$;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.fn.Consumer;
import br.jus.stf.plataforma.shared.indexacao.IndexadorRestAdapter;
import br.jus.stf.processamentoinicial.distribuicao.domain.model.Processo;
import br.jus.stf.processamentoinicial.distribuicao.infra.configuration.DistribuicaoConfiguration;

/**
 * Consumidor de eventos de aplicação para enviar para indexação
 * 
 * @author Lucas.Rodrigues
 *
 */
@Component
public class ProcessoIndexadorConsumer implements Consumer<Event<Processo>>, InitializingBean {
	
	@Autowired
	private EventBus eventBus;
	
	@Autowired
	private IndexadorRestAdapter indexadorRestAdapter;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		eventBus.on($("indexadorEventBus"), this);
	}
	
	@Override
	public void accept(Event<Processo> event) {
		Processo processo = event.getData();
		try {
			indexadorRestAdapter.indexar(DistribuicaoConfiguration.INDICE, processo);
		} catch (Exception e) {
			event.consumeError(e);
		}
	}

}
