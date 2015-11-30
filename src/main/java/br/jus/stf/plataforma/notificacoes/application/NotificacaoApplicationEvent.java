package br.jus.stf.plataforma.notificacoes.application;

import java.util.List;

import br.jus.stf.plataforma.notificacoes.domain.model.Notificacao;
import br.jus.stf.plataforma.notificacoes.domain.model.NotificacaoId;

/**
 * Interface que define os eventos publicados pela aplicação
 * 
 * @author Lucas Rodrigues
 */
public interface NotificacaoApplicationEvent {
	
	public void notificacaoEnviada(List<Notificacao> notificacoes);
	public void notificacaoLida(NotificacaoId id);
	
}
