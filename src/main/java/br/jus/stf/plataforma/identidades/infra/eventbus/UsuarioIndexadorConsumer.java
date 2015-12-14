package br.jus.stf.plataforma.identidades.infra.eventbus;

import static reactor.bus.selector.Selectors.$;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.fn.Consumer;
import br.jus.stf.plataforma.acessos.domain.model.Usuario;
import br.jus.stf.plataforma.identidades.infra.configuration.IdentidadesConfiguration;
import br.jus.stf.plataforma.shared.indexacao.IndexadorRestAdapter;

/**
 * Indexa dados de usuários cadastrados.
 * 
 * @author Anderson.Araujo
 * 
 * @since 14.12.2015
 *
 */
@Component
public class UsuarioIndexadorConsumer implements Consumer<Event<Usuario>>, InitializingBean {
	
	@Autowired
	private EventBus eventBus;
	
	@Autowired
	private IndexadorRestAdapter indexadorRestAdapter;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		eventBus.on($("indexadorEventBus"), this);
	}
	
	@Override
	public void accept(Event<Usuario> event) {
		Usuario usuario = event.getData();
		
		try {
			indexadorRestAdapter.indexar(IdentidadesConfiguration.INDICE_USUARIO, usuario);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
