package br.jus.stf.plataforma.pesquisas.infra.configuration;

import org.elasticsearch.client.Client;

/**
 * Permite configurar o ElasticSearch no afterPropertiesSet da classe de
 * configuração.
 * 
 * @author Tomas.Godoi
 *
 */
public interface ElasticsearchAfterPropertiesSetConfigurer {

	void afterPropertiesSet() throws Exception;

	void afterPropertiesSet(Client elasticsearchClient) throws Exception;

}
