package br.jus.stf.plataforma.notificacoes.domain.model;

import br.jus.stf.plataforma.notificacoes.domain.exception.NotificacaoServiceException;

/**
 * @author Lucas.Rodrigues
 *
 */
public interface NotificacaoService {

	void emitir(Notificacao notificacao) throws NotificacaoServiceException;
	
}
