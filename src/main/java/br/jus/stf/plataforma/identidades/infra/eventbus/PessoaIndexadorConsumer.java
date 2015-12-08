package br.jus.stf.plataforma.identidades.infra.eventbus;

import static reactor.bus.selector.Selectors.$;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.fn.Consumer;
import br.jus.stf.plataforma.identidades.domain.model.Pessoa;
import br.jus.stf.plataforma.identidades.infra.configuration.IdentidadesConfiguration;
import br.jus.stf.plataforma.shared.indexacao.IndexadorRestAdapter;

/**
 * Consumidor de eventos de aplicação para enviar para indexação
 * 
 * @author Lucas.Rodrigues
 *
 */
@Component
public class PessoaIndexadorConsumer implements Consumer<Event<Pessoa>>, InitializingBean {

	private static final String TIPO_ADVOGADO = "Advogado";
	
	@Autowired
	private EventBus eventBus;
	
	@Autowired
	private IndexadorRestAdapter indexadorRestAdapter;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		eventBus.on($("indexadorEventBus"), this);
	}
	
	@Override
	public void accept(Event<Pessoa> event) {
		Pessoa pessoa = event.getData();
		try {
			indexadorRestAdapter.indexar(IdentidadesConfiguration.INDICE_PESSOA, pessoa);
			if (pessoa.ehAdvogado()) {
				indexadorRestAdapter.indexar(IdentidadesConfiguration.INDICE_ADVOGADO, pessoa.id().toString(), TIPO_ADVOGADO, montarAdvogado(pessoa));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private Map<String, Object> montarAdvogado(Pessoa pessoa) {
		Map<String, Object> advogado = new HashMap<String, Object>();
		advogado.put("pessoaId", pessoa.id());
		advogado.put("nome", pessoa.nome());
		advogado.put("oab", pessoa.oab());
		advogado.put("cpf", pessoa.cpf());
		return advogado;
	}

}
