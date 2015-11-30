package br.jus.stf.plataforma.notificacoes.domain.model;

import br.jus.stf.plataforma.notificacoes.domain.exception.NotificacaoServiceException;

/**
 * @author Lucas.Rodrigues
 *
 */
public interface NotificacaoService {

	/**
	 * Emite uma notificação
	 * 
	 * @param notificacao
	 * @throws NotificacaoServiceException
	 */
	void emitir(Notificacao notificacao) throws NotificacaoServiceException;
	
	boolean permitidoEmitir();
}
