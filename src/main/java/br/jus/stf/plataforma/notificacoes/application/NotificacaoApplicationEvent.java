package br.jus.stf.plataforma.notificacoes.application;

import br.jus.stf.plataforma.notificacoes.domain.model.Notificacao;
import br.jus.stf.plataforma.notificacoes.domain.model.NotificacaoId;

/**
 * Interface que define os eventos publicados pela aplicação
 * 
 * @author Lucas Rodrigues
 */
public interface NotificacaoApplicationEvent {
	
	public void notificacaoEnviada(Notificacao notificacao);
	public void notificacaoLida(NotificacaoId id);
	
}
