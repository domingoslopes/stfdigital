package br.jus.stf.plataforma.pesquisas.infra.configuration;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.io.FileSystemUtils;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.env.Environment;
import org.elasticsearch.plugins.PluginManager;
import org.elasticsearch.plugins.PluginManager.OutputMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.jus.stf.plataforma.shared.settings.Profiles;

/**
 * Configura plugins para uso em desenvolvimento
 * 
 * @author Lucas.Rodrigues
 *
 */
@Configuration
@Profile(Profiles.DESENVOLVIMENTO)
public class ElasticsearchLocalConfiguration {

	@Autowired
	private Client elasticsearchClient;
	
    @PostConstruct
	private void configure() throws IOException {		
		Environment env = new Environment(elasticsearchClient.settings());
		
		if (env.dataFiles().length > 0) {
			FileSystemUtils.deleteRecursively(env.dataFiles());
		}
		
		if (!env.pluginsFile().exists()) {
	        FileSystemUtils.mkdirs(env.pluginsFile());
	        PluginManager pluginManager = new PluginManager(env, null, OutputMode.DEFAULT, TimeValue.timeValueMinutes(2));
	        pluginManager.downloadAndExtract("mobz/elasticsearch-head");
		}
	}
	
}
