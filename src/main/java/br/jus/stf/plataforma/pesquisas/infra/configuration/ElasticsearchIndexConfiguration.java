package br.jus.stf.plataforma.pesquisas.infra.configuration;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.pesquisas.domain.model.pesquisa.Pesquisa;

/**
 * Configure os índices básicos do ElasticSearch.
 * 
 * @author Lucas.Rodrigues
 * @author Tomas.Godoi
 */
@Component
@EnableElasticsearchRepositories("br.jus.stf.plataforma.pesquisas")
public class ElasticsearchIndexConfiguration {

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
