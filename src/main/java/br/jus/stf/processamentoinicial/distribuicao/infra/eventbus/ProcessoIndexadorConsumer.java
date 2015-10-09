package br.jus.stf.processamentoinicial.distribuicao.infra.eventbus;

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
import br.jus.stf.processamentoinicial.distribuicao.domain.model.Processo;

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
public class ProcessoIndexadorConsumer implements Consumer<Event<Processo>>, InitializingBean {

	private static String INDICE = "distribuicao";
	
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
	public void accept(Event<Processo> event) {
		Processo processo = event.getData();
		try {
			IndexarCommand indexarCommand = criarComando(processo);
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
	private IndexarCommand criarComando(Processo processo) throws IOException {
		IndexarCommand command = new IndexarCommand();
		command.setId(processo.id().toString());
		command.setTipo(processo.getClass().getSimpleName());
		command.setIndice(INDICE);
		command.setObjeto(criarJson(processo));
		return command;
	}
	
	/**
	 * Cria um json para ser indexado
	 * 
	 * @param peticao
	 * @return
	 * @throws IOException
	 */
	private JsonNode criarJson(Processo processo) throws IOException {
		String jsonString = objectMapper.writeValueAsString(processo);
		return objectMapper.readTree(jsonString);
	}

}
