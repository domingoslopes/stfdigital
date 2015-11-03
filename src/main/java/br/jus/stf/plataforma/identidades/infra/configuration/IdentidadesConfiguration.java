package br.jus.stf.plataforma.identidades.infra.configuration;

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
public class IdentidadesConfiguration {
	
	public static final String INDICE = "pessoa";
	private static final String PESSOA_RESOURCE = "/indices/identidades/pessoa.json"; 
	
	@Autowired
	private IndexadorRestAdapter indexadorRestAdapter;
	
	@PostConstruct
	private void configure() throws Exception {
		String configuracao = ResourceFileUtils.read(PESSOA_RESOURCE);			
		indexadorRestAdapter.criarIndice(INDICE, configuracao);
	}
	
}
