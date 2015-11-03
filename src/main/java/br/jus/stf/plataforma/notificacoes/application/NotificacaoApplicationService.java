package br.jus.stf.plataforma.notificacoes.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.jus.stf.plataforma.notificacoes.domain.model.Notificacao;
import br.jus.stf.plataforma.notificacoes.domain.model.NotificacaoId;
import br.jus.stf.plataforma.notificacoes.domain.model.TipoNotificacao;

/**
 * @author Lucas.Rodrigues
 *
 */
@Service
public class NotificacaoApplicationService {
	
	@Autowired
	private NotificacaoApplicationEvent notificacaoApplicationEvent;

	public void marcarLidas(final List<NotificacaoId> ids) {
		ids.forEach(id -> {
			notificacaoApplicationEvent.notificacaoLida(id);
		});
	}

	public void notificar(final TipoNotificacao tipo, final String mensagem, final List<String> notificados) {
		notificados.forEach(notificado -> {
			Notificacao notificacao = new Notificacao(tipo, mensagem, notificado);
			notificacaoApplicationEvent.notificacaoEnviada(notificacao);
		});
	}
	
}
