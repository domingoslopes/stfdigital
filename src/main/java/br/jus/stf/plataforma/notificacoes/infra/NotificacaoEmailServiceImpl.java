package br.jus.stf.plataforma.notificacoes.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.notificacoes.domain.exception.NotificacaoServiceException;
import br.jus.stf.plataforma.notificacoes.domain.model.Notificacao;
import br.jus.stf.plataforma.notificacoes.domain.model.NotificacaoEmailService;
import br.jus.stf.plataforma.shared.security.stereotype.Resource;

/**
 * @author Lucas.Rodrigues
 *
 */
@Component
public class NotificacaoEmailServiceImpl extends Resource<NotificacaoEmailServiceImpl> implements NotificacaoEmailService {

	private static final String ASSUNTO = "STF - Notificação";
	
	@Autowired
	private JavaMailSender mailSender;
	
	public NotificacaoEmailServiceImpl() {
		super("NOTIFICAR-EMAIL");
	}
	
	@Override
	public void emitir(Notificacao notificacao) throws NotificacaoServiceException {
		try {
			SimpleMailMessage msg = new SimpleMailMessage();
			msg.setTo(notificacao.notificado());
			msg.setFrom(notificacao.notificante() + "@stf.jus.br");
			msg.setText(notificacao.mensagem());
			msg.setSubject(ASSUNTO);
			mailSender.send(msg);
		} catch(RuntimeException re) {
			throw new NotificacaoServiceException(re);
		}
	}

}
