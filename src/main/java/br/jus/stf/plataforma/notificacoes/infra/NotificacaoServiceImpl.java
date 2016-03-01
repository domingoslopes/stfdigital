package br.jus.stf.plataforma.notificacoes.infra;

import br.jus.stf.plataforma.notificacoes.domain.model.NotificacaoService;
import br.jus.stf.plataforma.shared.security.resource.ResourceImpl;
import br.jus.stf.plataforma.shared.security.resource.ResourceType;

/**
 * Notificacao abstrata que apenas define o tipo do recurso
 * 
 * @author Lucas.Rodrigues
 *
 */
public abstract class NotificacaoServiceImpl extends ResourceImpl implements NotificacaoService {

	public NotificacaoServiceImpl(String id) {
		super(id, ResourceType.NOTIFICACAO);
	}

}
