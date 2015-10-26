package br.jus.stf.processamentoinicial.autuacao.infra.configuration;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import br.jus.stf.plataforma.shared.indexacao.IndexadorRestAdapter;
import br.jus.stf.plataforma.shared.util.ResourceFileUtils;

/**
 * @author Lucas.Rodrigues
 *
 */
@Configuration
public class AutuacaoConfiguration {
	
	public static final String INDICE = "autuacao";
	private static final String AUTUACAO_RESOURCE = "/indices/processamentoinicial/autuacao.json";
	
	@Autowired
	private IndexadorRestAdapter indexadorRestAdapter;
	
	@PostConstruct
	private void configure() throws Exception {
		String configuracao = ResourceFileUtils.read(AUTUACAO_RESOURCE);			
		indexadorRestAdapter.criarIndice(INDICE, configuracao);
	}
	
}
