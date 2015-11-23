package br.jus.stf.processamentoinicial.autuacao.infra.configuration;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import br.jus.stf.plataforma.shared.indexacao.IndexadorRestAdapter;
import br.jus.stf.plataforma.shared.util.ResourceFileUtils;

/**
 * Configura um índice para a busca de indicadores do processo de autuação.
 * 
 * @author Anderson Araújo
 * @since 09.11.2015
 *
 */
@Configuration
public class IndicadoresAutuacaoConfiguration {
	
	/**
	 * Adotou-se, para o nome do índice, o mesmo nome do arquivo .json.
	 */
	private static final String INDICADORES_AUTUACAO_RESOURCES = "/indices/processamentoinicial/quantidade-autuacoes.json";
	public static final String INDICE = "quantidade-autuacoes";
	
	@Autowired
	private IndexadorRestAdapter indexadorRestAdapter;
	
	@PostConstruct
	private void configure() throws Exception {
		
		String configuracao = ResourceFileUtils.read(INDICADORES_AUTUACAO_RESOURCES);
		this.indexadorRestAdapter.criarIndice(INDICE, configuracao);
	}
}
