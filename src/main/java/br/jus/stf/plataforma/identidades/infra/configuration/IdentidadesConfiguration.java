package br.jus.stf.plataforma.identidades.infra.configuration;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;

import br.jus.stf.plataforma.identidades.infra.IndexadorRestAdapter;

/**
 * @author Lucas.Rodrigues
 *
 */
@Configuration
public class IdentidadesConfiguration {
	
	@Autowired
	private IndexadorRestAdapter indexadorRestAdapter;
	
	@PostConstruct
	private void configure() throws Exception {
				
		ClassPathResource resource = new ClassPathResource("/indices/identidades/pessoa.json");
		String configuracao = FileUtils.readFileToString(resource.getFile());
		configuracao = StringUtils.trimAllWhitespace(configuracao);
			
		indexadorRestAdapter.criarIndice("pessoa", configuracao);
	}
	
}
