package br.jus.stf.pesquisa.infra.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * @author Lucas.Rodrigues
 *
 */
@Configuration
@EnableElasticsearchRepositories("br.jus.stf.pesquisa")
//@EnableSpringDataWebSupport // Habilitar ao separar projeto, usado para paginação
public class PesquisaConfiguration {
	
}
