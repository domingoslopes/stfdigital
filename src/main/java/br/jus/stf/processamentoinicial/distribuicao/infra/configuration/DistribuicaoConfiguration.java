package br.jus.stf.processamentoinicial.distribuicao.infra.configuration;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;

import br.jus.stf.processamentoinicial.distribuicao.infra.IndexadorRestAdapter;

/**
 * @author Lucas.Rodrigues
 *
 */
@Configuration
public class DistribuicaoConfiguration {
	
	@Autowired
	private IndexadorRestAdapter indexadorRestAdapter;
	
	@PostConstruct
	private void configure() throws Exception {
				
		ClassPathResource resource = new ClassPathResource("/indices/processamentoinicial/distribuicao.json");
		String configuracao = FileUtils.readFileToString(resource.getFile());
		configuracao = StringUtils.trimAllWhitespace(configuracao);
			
		indexadorRestAdapter.criarIndice("distribuicao", configuracao);
	}
	
}
