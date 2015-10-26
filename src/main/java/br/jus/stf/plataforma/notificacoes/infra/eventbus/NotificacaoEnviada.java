package br.jus.stf.plataforma.notificacoes.infra.eventbus;

import br.jus.stf.plataforma.notificacoes.domain.model.Notificacao;

/**
 * @author Lucas.Rodrigues
 *
 */
public class NotificacaoEnviada {
	
	private Notificacao notificacao;
	
	public NotificacaoEnviada(Notificacao notificacao) {
		this.notificacao = notificacao;
	}
	
	public Notificacao notificacao() {
		return notificacao;
	}

}
