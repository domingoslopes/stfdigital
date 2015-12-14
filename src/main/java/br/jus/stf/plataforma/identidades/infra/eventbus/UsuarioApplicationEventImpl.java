package br.jus.stf.plataforma.identidades.infra.eventbus;

import org.springframework.beans.factory.annotation.Autowired;

import reactor.bus.Event;
import reactor.bus.EventBus;
import br.jus.stf.plataforma.acessos.domain.model.Usuario;
import br.jus.stf.plataforma.identidades.application.UsuarioApplicationEvent;

/**
 * Registra eventos associados a usuários.
 * 
 * @author Anderson.Araujo
 * 
 * @since 14.12.2015
 *
 */
public class UsuarioApplicationEventImpl implements UsuarioApplicationEvent{
	
	@Autowired
	private EventBus eventBus;
	
	@Override
	public void usuarioCadastrado(Usuario usuario) {
		eventBus.notify("indexadorEventBus", Event.wrap(usuario));
	}
}
