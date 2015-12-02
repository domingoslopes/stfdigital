package br.jus.stf.plataforma.pesquisas.infra.configuration;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchAutoConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * Configura plugins do Elasticsearch
 * 
 * @author Lucas.Rodrigues
 * @author Tomas.Godoi
 */
@Configuration
public class ElasticsearchConfiguration extends ElasticsearchAutoConfiguration implements InitializingBean {

	@Autowired(required = false)
	private ElasticsearchAfterPropertiesSetConfigurer configurer;

	@Override
	public void afterPropertiesSet() throws Exception {
		if (configurer != null) {
			configurer.afterPropertiesSet();
			configurer.afterPropertiesSet(elasticsearchClient());
		}
	}

}
