package br.jus.stf.processamentoinicial.autuacao.infra.configuration;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import br.jus.stf.processamentoinicial.autuacao.infra.IndexadorRestAdapter;

/**
 * @author Lucas.Rodrigues
 *
 */
@Configuration
public class AutuacaoConfiguration {
	
	private static final String AUTUACAO_RESOURCE = "/indices/processamentoinicial/autuacao.json";
	
	@Autowired
	private IndexadorRestAdapter indexadorRestAdapter;
	
	@PostConstruct
	private void configure() throws Exception {
		String configuracao = readConfiguration(AUTUACAO_RESOURCE);			
		indexadorRestAdapter.criarIndice("autuacao", configuracao);
	}
	
	/**
	 * Retorna uma string do conteúdo do arquivo
	 * 
	 * @param location
	 * @return
	 * @throws IOException 
	 */
	private String readConfiguration(String location) throws IOException {
		InputStream input = getClass().getResourceAsStream(location);
		return StringUtils.trimAllWhitespace(IOUtils.toString(input));
	}
	
}
