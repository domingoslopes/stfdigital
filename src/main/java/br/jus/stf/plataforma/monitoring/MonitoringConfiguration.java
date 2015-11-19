package br.jus.stf.plataforma.monitoring;

import static org.springframework.util.StringUtils.trimAllWhitespace;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import br.jus.stf.plataforma.shared.indexacao.IndexadorRestAdapter;

/**
 * Registra, no Elasticsearch, o índice para registros das métricas de utilização do sistema.
 * 
 * @author Rodrigo Barreiros
 *
 * @since 1.0.0.M4
 * @since 22.10.2015
 */
@Configuration
public class MonitoringConfiguration {
	
	public static final String INDICE_UTILIZACAO = "utilizacao";

	private static final String JSON_PATH = "/indices/monitoring/utilizacao.json";
	
	@Autowired
	private IndexadorRestAdapter indexadorRestAdapter;
	
	/**
	 * O formato do índice é definido no arquivo JSon {@value #JSON_PATH}.
	 * 
	 * @throws Exception caso ocorra alguma problema ao ler o arquivo 
	 * JSon ou ao tentar criar o índice no Elasticsearch
	 */
	@PostConstruct
	private void configure() throws Exception {
		String configuracao = trimAllWhitespace(IOUtils.toString(getClass().getResourceAsStream(JSON_PATH)));
		
		indexadorRestAdapter.criarIndice(INDICE_UTILIZACAO, configuracao);
	}
	
}
