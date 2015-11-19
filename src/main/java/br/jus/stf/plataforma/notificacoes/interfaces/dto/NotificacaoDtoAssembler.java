package br.jus.stf.plataforma.notificacoes.interfaces.dto;

import java.util.Date;

import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.notificacoes.domain.model.Notificacao;

/**
 * @author Lucas.Rodrigues
 *
 */
@Component
public class NotificacaoDtoAssembler {

	public NotificacaoDto toDto(Notificacao notificacao, boolean lida) {
		String id = notificacao.id().toString();
		String mensagem = notificacao.mensagem();
		Date data = notificacao.dataCriacao();
		return new NotificacaoDto(id, mensagem, data, lida);
	}
	
}
