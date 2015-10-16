package br.jus.stf.processamentoinicial.distribuicao.infra.configuration;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import br.jus.stf.processamentoinicial.distribuicao.infra.IndexadorRestAdapter;

/**
 * @author Lucas.Rodrigues
 *
 */
@Configuration
public class DistribuicaoConfiguration {
	
	private static final String DISTRIBUICAO_RESOURCE = "/indices/processamentoinicial/distribuicao.json";
	
	@Autowired
	private IndexadorRestAdapter indexadorRestAdapter;
	
	@PostConstruct
	private void configure() throws Exception {
		String configuracao = readConfiguration(DISTRIBUICAO_RESOURCE);
		indexadorRestAdapter.criarIndice("distribuicao", configuracao);
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
