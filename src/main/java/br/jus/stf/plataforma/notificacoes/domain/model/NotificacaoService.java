package br.jus.stf.plataforma.notificacoes.domain.model;

import br.jus.stf.plataforma.notificacoes.domain.exception.NotificacaoServiceException;
import br.jus.stf.plataforma.shared.security.stereotype.Secured;

/**
 * @author Lucas.Rodrigues
 *
 */
public interface NotificacaoService extends Secured {

	/**
	 * Emite uma notificação
	 * 
	 * @param notificacao
	 * @throws NotificacaoServiceException
	 */
	void emitir(Notificacao notificacao) throws NotificacaoServiceException;
	
}
