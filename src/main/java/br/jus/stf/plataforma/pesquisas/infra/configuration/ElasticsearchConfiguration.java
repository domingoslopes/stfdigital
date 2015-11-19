package br.jus.stf.plataforma.pesquisas.infra.configuration;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import br.jus.stf.plataforma.pesquisas.domain.model.query.Pesquisa;

/**
 * @author Lucas Rodrigues
 */
@Configuration
@EnableElasticsearchRepositories("br.jus.stf.plataforma.pesquisas")
//@EnableSpringDataWebSupport // Habilitar ao separar projeto, usado para paginação
public class ElasticsearchConfiguration {
	
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	
    @PostConstruct
	private void configure() {
		if (!elasticsearchTemplate.indexExists(Pesquisa.class)) {
			elasticsearchTemplate.createIndex(Pesquisa.class);
		}
		elasticsearchTemplate.putMapping(Pesquisa.class);
		elasticsearchTemplate.refresh(Pesquisa.class, true);
	}
	
}
