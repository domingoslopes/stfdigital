package br.jus.stf.plataforma.notificacoes.domain.model;

import br.jus.stf.plataforma.notificacoes.domain.exception.NotificacaoServiceException;
import br.jus.stf.plataforma.shared.security.stereotype.Resource;

/**
 * @author Lucas.Rodrigues
 *
 */
public interface NotificacaoService extends Resource {

	/**
	 * Emite uma notificação
	 * 
	 * @param notificacao
	 * @throws NotificacaoServiceException
	 */
	void emitir(Notificacao notificacao) throws NotificacaoServiceException;
	
}
