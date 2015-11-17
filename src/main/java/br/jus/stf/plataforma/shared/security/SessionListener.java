package br.jus.stf.plataforma.shared.security;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.stereotype.Component;

/**
 * Não precisa de timeout pois vai ser encerrada a sessão quando
 * o websocket for desconectado
 * 
 * @author Lucas.Rodrigues
 *
 */
@Component
public class SessionListener implements HttpSessionListener {
 
    @Override
    public void sessionCreated(HttpSessionEvent event) {
        event.getSession().setMaxInactiveInterval(60);
    }
 
    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
    }
}
