package br.jus.stf.processamentoinicial.autuacao.infra.configuration;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;

import br.jus.stf.processamentoinicial.autuacao.infra.IndexadorRestAdapter;

/**
 * @author Lucas.Rodrigues
 *
 */
@Configuration
public class AutuacaoConfiguration {
	
	@Autowired
	private IndexadorRestAdapter indexadorRestAdapter;
	
	@PostConstruct
	private void configure() throws Exception {
				
		if (!indexadorRestAdapter.existeIndice("autuacao")) {
			ClassPathResource resource = new ClassPathResource("/indices/autuacao.json");
			String configuracao = FileUtils.readFileToString(resource.getFile());
			configuracao = StringUtils.trimAllWhitespace(configuracao);
			
			indexadorRestAdapter.criarIndice("autuacao", configuracao);
		}
	}
	
}
