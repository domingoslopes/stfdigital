package br.jus.stf.plataforma.notificacoes.infra.configuration;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

import br.jus.stf.plataforma.shared.indexacao.IndexadorRestAdapter;
import br.jus.stf.plataforma.shared.util.ResourceFileUtils;

import com.icegreen.greenmail.store.FolderException;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;

/**
 * @author Lucas.Rodrigues
 *
 */
@Configuration
@EnableWebSocketMessageBroker
public class NotificacoesConfiguration {
	
	public static final String INDICE = "notificacao";
	private static final String NOTIFICACAO_RESOURCE = "/indices/notificacoes/notificacao.json"; 
	
	@Autowired
	private IndexadorRestAdapter indexadorRestAdapter;
		
	@PostConstruct
	private void configure() throws Exception {
		String configuracao = ResourceFileUtils.read(NOTIFICACAO_RESOURCE);
		indexadorRestAdapter.criarIndice(INDICE, configuracao);
	}
	
	@Bean(initMethod = "start", destroyMethod = "stop")
	public GreenMail greenMail() throws FolderException {
		return new GreenMail(ServerSetup.SMTP);
	}
	
	@Bean
	public JavaMailSender mailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(ServerSetup.getLocalHostAddress());
		mailSender.setPort(ServerSetup.SMTP.getPort());
		return mailSender;
	}

}
