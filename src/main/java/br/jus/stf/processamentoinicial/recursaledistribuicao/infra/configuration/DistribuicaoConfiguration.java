package br.jus.stf.processamentoinicial.recursaledistribuicao.infra.configuration;

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
public class DistribuicaoConfiguration {
	
	public static final String INDICE = "distribuicao";
	private static final String DISTRIBUICAO_RESOURCE = "/indices/processamentoinicial/distribuicao.json";
	
	@Autowired
	private IndexadorRestAdapter indexadorRestAdapter;
	
	@PostConstruct
	private void configure() throws Exception {
		String configuracao = ResourceFileUtils.read(DISTRIBUICAO_RESOURCE);
		indexadorRestAdapter.criarIndice(INDICE, configuracao);
	}
	
}
