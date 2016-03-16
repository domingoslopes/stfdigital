package br.jus.stf.processamentoinicial.autuacao.infra.eventbus;

import static reactor.bus.selector.Selectors.$;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.fn.Consumer;
import br.jus.stf.plataforma.shared.indexacao.IndexadorRestAdapter;
import br.jus.stf.processamentoinicial.autuacao.domain.model.Peticao;
import br.jus.stf.processamentoinicial.autuacao.infra.configuration.AutuacaoConfiguration;

@Component
public class PeticaoIndexadorConsumer implements Consumer<Event<PeticaoModificada>>, InitializingBean {

	@Autowired
	private EventBus eventBus;

	@Autowired
	private IndexadorRestAdapter indexadorRestAdapter;

	@Override
	public void afterPropertiesSet() throws Exception {
		eventBus.on($("indexadorEventBus"), this);
	}

	@Override
	public void accept(Event<PeticaoModificada> event) {

		Peticao peticao = event.getData().peticao();

		try {
			this.indexadorRestAdapter.indexar(AutuacaoConfiguration.INDICE, peticao);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
