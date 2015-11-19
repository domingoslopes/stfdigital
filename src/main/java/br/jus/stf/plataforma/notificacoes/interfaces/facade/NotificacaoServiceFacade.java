package br.jus.stf.plataforma.notificacoes.interfaces.facade;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.jus.stf.plataforma.notificacoes.application.NotificacaoApplicationService;
import br.jus.stf.plataforma.notificacoes.domain.model.NotificacaoId;
import br.jus.stf.plataforma.notificacoes.domain.model.TipoNotificacao;

/**
 * @author Lucas.Rodrigues
 *
 */
@Service
public class NotificacaoServiceFacade {

	@Autowired
	private NotificacaoApplicationService notificacaoApplicationService;
	
	/**
	 * Marca as notificações como lidas
	 * 
	 * @param notificacoes
	 * @param usuario
	 */
	public void marcarLidas(List<String> notificacoes) {
		List<NotificacaoId> notificacoesIds = notificacoes.stream()
				.map(id -> new NotificacaoId(UUID.fromString(id)))
				.collect(Collectors.toList());
		notificacaoApplicationService.marcarLidas(notificacoesIds);
	}

	/**
	 * Notifica um grupo de usuário com uma mensagem
	 * 
	 * @param tipo
	 * @param mensagem
	 * @param notificados
	 */
	public void notificar(String tipo, String mensagem, List<String> notificados) {
		TipoNotificacao tipoNotificacao = TipoNotificacao.valueOf(tipo);
		notificacaoApplicationService.notificar(tipoNotificacao, mensagem, notificados);
	}
	
}
