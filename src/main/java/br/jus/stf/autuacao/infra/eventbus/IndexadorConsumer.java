package br.jus.stf.autuacao.infra.eventbus;

import static reactor.bus.selector.Selectors.$;

import java.io.IOException;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;

import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.fn.Consumer;
import br.jus.stf.pesquisa.interfaces.IndexadorRestResource;
import br.jus.stf.pesquisa.interfaces.command.IndexarCommand;
import br.jus.stf.shared.domain.stereotype.Entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Consumidor de eventos de aplicação para enviar para indexação
 * 
 * @author Lucas.Rodrigues
 *
 */
@Component
public class IndexadorConsumer implements Consumer<Event<Entity<?, ?>>>, InitializingBean {

	private static String INDICE = "autuacao";
	
	@Autowired
	private EventBus eventBus;
	
	@Autowired
	private IndexadorRestResource indexadorRestResource;
	
	private ObjectMapper objectMapper;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		eventBus.on($("indexadorEventBus"), this);
		objectMapper = new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
		objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
	}
	
	@Override
	public void accept(Event<Entity<?, ?>> event) {
		Entity<?, ?> entity = event.getData();
		try {
			IndexarCommand indexarCommand = createCommand(entity);
			indexadorRestResource.indexar(INDICE, indexarCommand, new BeanPropertyBindingResult(indexarCommand, "indexarCommand"));
		} catch (Exception e) {
			//event.consumeError(e);
			throw new RuntimeException("Não foi possível indexar o objeto!", e);
		}
	}

	private IndexarCommand createCommand(Entity<?, ?> entity) throws IOException {
		IndexarCommand command = new IndexarCommand();
		command.setTipo(entity.getClass().getSimpleName());
		command.setId(entity.id().toString());
		String json = objectMapper.writeValueAsString(entity);
		command.setObjeto(objectMapper.readTree(json));
		return command;
	}

}
