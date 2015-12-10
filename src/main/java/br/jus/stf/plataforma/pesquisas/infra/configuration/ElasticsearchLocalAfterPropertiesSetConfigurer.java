package br.jus.stf.plataforma.pesquisas.infra.configuration;

import java.io.File;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.io.FileSystemUtils;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.env.Environment;
import org.elasticsearch.plugins.PluginManager;
import org.elasticsearch.plugins.PluginManager.OutputMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.shared.settings.Profiles;

/**
 * Configura plugins para uso em desenvolvimento
 * 
 * @author Lucas.Rodrigues
 * @author Tomas.Godoi
 */
@Component
@Profile(Profiles.DESENVOLVIMENTO)
public class ElasticsearchLocalAfterPropertiesSetConfigurer implements ElasticsearchAfterPropertiesSetConfigurer {

	private static final String ELASTIC_DATA_DIR = "target/elasticsearch/data";
	
	@Autowired
	private org.springframework.core.env.Environment env;
	
	@Autowired
	private ElasticsearchProperties elasticsearchProperties;

	@Override
	public void afterPropertiesSet() {
		if (!env.acceptsProfiles(Profiles.KEEP_DATA)) {
			// Apaga o diretório de dados.
			FileSystemUtils.deleteRecursively(new File(ELASTIC_DATA_DIR), true);
		} else {
			String userDir = System.getProperty("user.home");
			elasticsearchProperties.getProperties().put("path.data", userDir + "/stfdigital-data/elasticsearch/data");
			elasticsearchProperties.getProperties().put("path.logs", userDir + "/stfdigital-data/elasticsearch/logs");
			elasticsearchProperties.getProperties().put("path.plugins", userDir + "/stfdigital-data/elasticsearch/plugins");
		}
	}

	@Override
	public void afterPropertiesSet(Client elasticsearchClient) throws Exception {
		Environment env = new Environment(elasticsearchClient.settings());

		if (!env.pluginsFile().exists()) {
			FileSystemUtils.mkdirs(env.pluginsFile());
			PluginManager pluginManager = new PluginManager(env, null, OutputMode.DEFAULT,
					TimeValue.timeValueMinutes(2));
			pluginManager.downloadAndExtract("mobz/elasticsearch-head");
		}
	}

}
