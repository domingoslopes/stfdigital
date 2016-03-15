package br.jus.stf.processamentoinicial.autuacao.infra.eventbus;

import static reactor.bus.selector.Selectors.$;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BeanPropertyBindingResult;

import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.fn.Consumer;
import br.jus.stf.plataforma.notificacoes.interfaces.NotificacaoRestResource;
import br.jus.stf.plataforma.notificacoes.interfaces.command.NotificarCommand;
import br.jus.stf.processamentoinicial.autuacao.domain.model.Peticao;

/**
 * Notifica os usuário(s) sobre a rejeição de uma petição.
 * 
 * @author Anderson.Araujo
 * @since 05.11.2015
 *
 */
public class NotificadorPeticaoRejeitada implements Consumer<Event<PeticaoRejeitada>>, InitializingBean {

	@Autowired
	private EventBus eventBus;
	
	@Autowired
	private NotificacaoRestResource notificacaoRestResource;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		eventBus.on($("notificadorEventBus"), this);
	}

	@Override
	public void accept(Event<PeticaoRejeitada> evento) {
		
		Peticao peticao = evento.getData().peticao();
		
		try {
			NotificarCommand command = this.criarCommand(peticao);
			this.notificacaoRestResource.notificar(command, new BeanPropertyBindingResult(command, "notificarCommand"));
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Cria um objeto contendo os dados da notificação a ser enviado para o serviço de notificação.
	 * 
	 * @param peticao Dados da petição rejeitada.
	 * @return Objeto command usado para notificar um usuário.
	 */
	private NotificarCommand criarCommand(Peticao peticao){
		
		List<String> notificados = null;
		NotificarCommand command = null;
		
		notificados = new ArrayList<String>();
		notificados.add("gestor-autuacao");
		
		command = new NotificarCommand();
		command.setMensagem("A petição " + peticao.identificacao() + " foi rejeitada.");
		command.setNotificados(notificados);
		command.setTipo("UI");
		
		return command;
	}
}
