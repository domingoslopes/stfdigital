package br.jus.stf.processamentoinicial.autuacao.infra.eventbus;

import static reactor.bus.selector.Selectors.$;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.shared.indexacao.IndexadorRestAdapter;
import br.jus.stf.processamentoinicial.autuacao.infra.configuration.AutuacaoConfiguration;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.fn.Consumer;

@Component
public class PeticaoStatusIndexadorConsumer implements Consumer<Event<PeticaoStatusModificado>>, InitializingBean {

	@Autowired
	private EventBus eventBus;

	@Autowired
	private IndexadorRestAdapter indexadorRestAdapter;

	@Override
	public void afterPropertiesSet() throws Exception {
		eventBus.on($("indexadorEventBus"), this);
	}

	@Override
	public void accept(Event<PeticaoStatusModificado> event) {
		PeticaoStatusModificado psm = event.getData();
		
		Map<String, Object> mapaCamposAlterados = new HashMap<>();
		mapaCamposAlterados.put("status", psm.status().toString());
		
		String campoColecao = "processosWorkflow";
		String expressaoId = "id.sequencial";
		Long idItem = psm.processoWorkflowId().toLong();

		try {
			this.indexadorRestAdapter.atualizarItemDeColecao(AutuacaoConfiguration.INDICE, psm.peticaoId().toString(),
					psm.peticaoClass().getSimpleName(), campoColecao, expressaoId, idItem, mapaCamposAlterados);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
