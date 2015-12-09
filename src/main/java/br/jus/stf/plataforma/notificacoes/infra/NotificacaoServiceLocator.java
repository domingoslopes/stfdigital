package br.jus.stf.plataforma.notificacoes.infra;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.notificacoes.domain.model.NotificacaoService;
import br.jus.stf.plataforma.shared.security.stereotype.SecuredResource;

/**
 * @author Lucas.Rodrigues
 *
 */
@Component
public class NotificacaoServiceLocator {
	
	@Autowired
	private ApplicationContext applicationContext;

	@SecuredResource
	public Optional<NotificacaoService> getBean(Class<? extends NotificacaoService> clazz) {
		return Optional.of(applicationContext.getBean(clazz));
	}
	
}
