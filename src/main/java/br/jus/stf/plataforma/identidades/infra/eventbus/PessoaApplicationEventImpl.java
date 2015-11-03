package br.jus.stf.plataforma.identidades.infra.eventbus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.bus.Event;
import reactor.bus.EventBus;
import br.jus.stf.plataforma.identidades.application.PessoaApplicationEvent;
import br.jus.stf.plataforma.identidades.domain.model.Pessoa;

/**
 * Classe que implementa a publicação de eventos de pessoa
 * 
 * @author Lucas.Rodrigues
 *
 */
@Component
public class PessoaApplicationEventImpl implements PessoaApplicationEvent {

	@Autowired
	private EventBus eventBus;
	
	@Override
	public void pessoaCadastrada(Pessoa pessoa) {
		eventBus.notify("indexadorEventBus", Event.wrap(pessoa));
	}

}
