package br.jus.stf.processamentoinicial.autuacao.infra.eventbus;

import static reactor.bus.selector.Selectors.$;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;

import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.fn.Consumer;
import br.jus.stf.plataforma.notificacoes.interfaces.NotificacaoRestResource;
import br.jus.stf.plataforma.notificacoes.interfaces.command.NotificarCommand;

/**
 * Consumidor de eventos de aplicação para enviar para indexação
 * 
 * @author Lucas.Rodrigues
 *
 */
@Component
public class NotificadorConsumer implements Consumer<Event<Map<String , String>>>, InitializingBean {

	@Autowired
	private EventBus eventBus;
	
	@Autowired
	private NotificacaoRestResource notificacaoRestResource;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		eventBus.on($("notificadorEventBus"), this);
	}
	
	@Override
	public void accept(Event<Map<String, String>> event) {
		Map<String, String> notificacao = event.getData();
		try {
			NotificarCommand notificarCommand = criarComando(notificacao);
			notificacaoRestResource.notificar(notificarCommand, new BeanPropertyBindingResult(notificarCommand, "notificarCommand"));
		} catch (Exception e) {
			//event.consumeError(e);
			throw new RuntimeException("Não foi possível notificar!", e);
		}
	}

	/**
	 * @param peticao
	 * @return
	 * @throws IOException
	 */
	private NotificarCommand criarComando(Map<String, String> notificacao) throws IOException {
		NotificarCommand command = new NotificarCommand();
		command.setMensagem(notificacao.get("mensagem"));
		List<String> notificados = new ArrayList<String>();
		notificados.add(notificacao.get("notificado"));
		command.setNotificados(notificados);
		command.setTipo("UI");
		return command;
	}

}
