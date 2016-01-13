package br.jus.stf.plataforma.documentos.infra.configuration.fs;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.ftp.gateway.FtpOutboundGateway;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;
import org.springframework.messaging.MessageHandler;

import br.jus.stf.plataforma.shared.settings.Profiles;

@Configuration
@Profile(Profiles.DOCUMENTO_FS)
public class FTPConfiguration {

	private static final int BUFFER_SIZE = 100000;
	
	@Value("${ftp.host}")
	private String host;
	
	@Value("${ftp.port}")
	private int port;
	
	@Value("${ftp.username}")
	private String username;
	
	@Value("${ftp.password}")
	private String password;
	
	@Bean
	public DefaultFtpSessionFactory ftpClientFactory() {
		DefaultFtpSessionFactory ftpClientFactory = new DefaultFtpSessionFactory();
		ftpClientFactory.setHost(host);
		ftpClientFactory.setPort(port);
		ftpClientFactory.setUsername(username);
		ftpClientFactory.setPassword(password);
		ftpClientFactory.setClientMode(FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE);
		ftpClientFactory.setFileType(FTP.BINARY_FILE_TYPE);
		ftpClientFactory.setBufferSize(BUFFER_SIZE);
		
		return ftpClientFactory;
	}
	
	@Bean
	public MessageHandler ftpOutboundGateway() {
		FtpOutboundGateway ftpOutboundGateway = new FtpOutboundGateway(ftpClientFactory(), "", ""); // TODO Complementar a configuração.
		
		return ftpOutboundGateway;
	}
	
}
