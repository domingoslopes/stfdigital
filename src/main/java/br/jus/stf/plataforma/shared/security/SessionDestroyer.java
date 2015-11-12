package br.jus.stf.plataforma.shared.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 * Permite invalidar a sessão quando o websocket do cliente é desconectado,
 * assim não é necessário setar um timeout de sessão. O fato do websocket estar ativo
 * não faz com que a sessão do usuário seja mantida em caso de um timeout por falta de
 * requisições.
 * 
 * @author Lucas.Rodrigues
 *
 */
@Component
public class SessionDestroyer implements ApplicationListener<SessionDisconnectEvent> {
	
	@Autowired
	private SessionRegistry sessionRegistry;

	@Override
	public void onApplicationEvent(SessionDisconnectEvent event) {
		sessionRegistry.getAllSessions(getPrincipal(event.getMessage()), false)
			.forEach(info -> info.expireNow());
	}
	
	private Object getPrincipal(Message<byte[]> message) {
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
		return ((Authentication) accessor.getUser()).getPrincipal();
	}
	
}
