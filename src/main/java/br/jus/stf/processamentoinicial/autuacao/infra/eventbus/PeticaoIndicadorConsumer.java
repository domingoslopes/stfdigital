/**
 * 
 */
package br.jus.stf.processamentoinicial.autuacao.infra.eventbus;

import static reactor.bus.selector.Selectors.$;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import br.jus.stf.plataforma.shared.indexacao.IndexadorRestAdapter;
import br.jus.stf.processamentoinicial.autuacao.domain.model.Peticao;
import br.jus.stf.processamentoinicial.autuacao.infra.configuration.IndicadoresAutuacaoConfiguration;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.fn.Consumer;

/**
 * Registra a autuação de uma petição no elastic search.
 * 
 * @author Anderson Araújo
 * @since 09.11.2015
 *
 */
public class PeticaoIndicadorConsumer  implements Consumer<Event<PeticaoAutuada>>, InitializingBean {
	@Autowired
	private EventBus eventBus;
	
	@Autowired
	private IndexadorRestAdapter indexadorRestAdapter;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		eventBus.on($("indicadorEventBus"), this);
	}

	@Override
	public void accept(Event<PeticaoAutuada> evento) {
		
		Peticao peticao = evento.getData().peticao();
		
		try {
			this.indexadorRestAdapter.indexar(IndicadoresAutuacaoConfiguration.INDICE, peticao);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
