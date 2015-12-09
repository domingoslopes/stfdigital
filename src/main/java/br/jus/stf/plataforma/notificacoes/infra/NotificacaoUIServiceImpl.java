package br.jus.stf.plataforma.notificacoes.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.notificacoes.domain.exception.NotificacaoServiceException;
import br.jus.stf.plataforma.notificacoes.domain.model.Notificacao;
import br.jus.stf.plataforma.notificacoes.domain.model.NotificacaoUIService;
import br.jus.stf.plataforma.notificacoes.infra.configuration.NotificacoesConfiguration;
import br.jus.stf.plataforma.notificacoes.interfaces.dto.NotificacaoDto;
import br.jus.stf.plataforma.notificacoes.interfaces.dto.NotificacaoDtoAssembler;
import br.jus.stf.plataforma.shared.indexacao.IndexadorRestAdapter;
import br.jus.stf.plataforma.shared.security.stereotype.Resource;

/**
 * @author Lucas.Rodrigues
 *
 */
@Component
public class NotificacaoUIServiceImpl extends Resource<NotificacaoUIServiceImpl> implements NotificacaoUIService {

	private static final String DESTINO = "/notificacoes"; 
	
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	@Autowired
	private NotificacaoDtoAssembler notificacaoDtoAssembler;

	@Autowired
	private IndexadorRestAdapter indexadorRestAdapter;
	
	public NotificacaoUIServiceImpl() {
		super("NOTIFICAR-UI");
	}
	
	@Override
	public void emitir(Notificacao notificacao) throws NotificacaoServiceException {
		NotificacaoDto dto = notificacaoDtoAssembler.toDto(notificacao, false);
		try {
			indexadorRestAdapter.indexar(NotificacoesConfiguration.INDICE, notificacao);
		} catch (Exception e) {
			throw new NotificacaoServiceException(e);
		}
		messagingTemplate.convertAndSendToUser(notificacao.notificado(), DESTINO, dto);
	}

}
