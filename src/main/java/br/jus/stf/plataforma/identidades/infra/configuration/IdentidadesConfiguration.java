package br.jus.stf.plataforma.identidades.infra.configuration;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import br.jus.stf.plataforma.identidades.infra.IndexadorRestAdapter;

/**
 * @author Lucas.Rodrigues
 *
 */
@Configuration
public class IdentidadesConfiguration {
	
	private static final String PESSOA_RESOURCE = "/indices/identidades/pessoa.json"; 
	
	@Autowired
	private IndexadorRestAdapter indexadorRestAdapter;
	
	@PostConstruct
	private void configure() throws Exception {
		String configuracao = readConfiguration(PESSOA_RESOURCE);			
		indexadorRestAdapter.criarIndice("pessoa", configuracao);
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
