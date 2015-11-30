package br.jus.stf.plataforma.notificacoes.infra.eventbus;

import java.util.List;

import br.jus.stf.plataforma.notificacoes.domain.model.Notificacao;

/**
 * @author Lucas.Rodrigues
 *
 */
public class NotificacaoEnviada {
	
	private List<Notificacao> notificacoes;
	
	public NotificacaoEnviada(List<Notificacao> notificacoes) {
		this.notificacoes = notificacoes;
	}
	
	public List<Notificacao> notificacoes() {
		return notificacoes;
	}

}
