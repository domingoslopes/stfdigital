package br.jus.stf.processamentoinicial.autuacao.infra.eventbus;

import static reactor.bus.selector.Selectors.$;

import java.io.IOException;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;

import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.fn.Consumer;
import br.jus.stf.plataforma.pesquisas.interfaces.IndexadorRestResource;
import br.jus.stf.plataforma.pesquisas.interfaces.command.IndexarCommand;
import br.jus.stf.processamentoinicial.autuacao.domain.model.Peticao;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Consumidor de eventos de aplicação para enviar para indexação
 * 
 * @author Lucas.Rodrigues
 *
 */
@Component
public class PeticaoIndexadorConsumer implements Consumer<Event<Peticao>>, InitializingBean {

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
	public void accept(Event<Peticao> event) {
		Peticao peticao = event.getData();
		try {
			IndexarCommand indexarCommand = criarComando(peticao);
			indexadorRestResource.indexar(indexarCommand, new BeanPropertyBindingResult(indexarCommand, "indexarCommand"));
		} catch (Exception e) {
			//event.consumeError(e);
			throw new RuntimeException("Não foi possível indexar o objeto!", e);
		}
	}

	/**
	 * @param peticao
	 * @return
	 * @throws IOException
	 */
	private IndexarCommand criarComando(Peticao peticao) throws IOException {
		IndexarCommand command = new IndexarCommand();
		command.setId(peticao.id().toString());
		command.setTipo(peticao.getClass().getSimpleName());
		command.setIndice(INDICE);
		command.setObjeto(criarJson(peticao));
		return command;
	}
	
	/**
	 * Cria um json para ser indexado
	 * 
	 * @param peticao
	 * @return
	 * @throws IOException
	 */
	private JsonNode criarJson(Peticao peticao) throws IOException {
		String jsonString = objectMapper.writeValueAsString(peticao);
		return objectMapper.readTree(jsonString);
	}

}
