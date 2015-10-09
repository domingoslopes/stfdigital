package br.jus.stf.plataforma.identidades.infra.eventbus;

import static reactor.bus.selector.Selectors.$;

import java.io.IOException;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;

import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.fn.Consumer;
import br.jus.stf.plataforma.identidades.domain.model.Pessoa;
import br.jus.stf.plataforma.pesquisas.interfaces.IndexadorRestResource;
import br.jus.stf.plataforma.pesquisas.interfaces.command.IndexarCommand;

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
public class PessoaIndexadorConsumer implements Consumer<Event<Pessoa>>, InitializingBean {

	private static String INDICE = "pessoa";
	
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
	public void accept(Event<Pessoa> event) {
		Pessoa pessoa = event.getData();
		try {
			IndexarCommand indexarCommand = criarComando(pessoa);
			indexadorRestResource.indexar(indexarCommand, new BeanPropertyBindingResult(indexarCommand, "indexarCommand"));
		} catch (Exception e) {
			//event.consumeError(e);
			throw new RuntimeException("Não foi possível indexar o objeto!", e);
		}
	}

	/**
	 * @param pessoa
	 * @return
	 * @throws IOException
	 */
	private IndexarCommand criarComando(Pessoa pessoa) throws IOException {
		IndexarCommand command = new IndexarCommand();
		command.setId(pessoa.id().toString());
		command.setTipo(pessoa.getClass().getSimpleName());
		command.setIndice(INDICE);
		command.setObjeto(criarJson(pessoa));
		return command;
	}
	
	/**
	 * Cria um json para ser indexado
	 * 
	 * @param pessoa
	 * @return
	 * @throws IOException
	 */
	private JsonNode criarJson(Pessoa pessoa) throws IOException {
		String jsonString = objectMapper.writeValueAsString(pessoa);
		return objectMapper.readTree(jsonString);
	}

}
