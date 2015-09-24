package br.jus.stf.generico.infra.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import br.jus.stf.plataforma.settings.Profiles;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

/**
 * @author Rafael.Alencar
 *
 */
@Configuration
@Profile(Profiles.PRODUCAO)
public class MongoConfiguration extends AbstractMongoConfiguration {

	@Bean
	public GridFsTemplate gridFsTemplate() throws Exception {
		GridFsTemplate gridTemplate = new GridFsTemplate(mongoDbFactory(), mappingMongoConverter());
		
		return gridTemplate;
	}

	@Override
	protected String getDatabaseName() {
		return "test";
	}
	
	@Override
	@Bean
	public Mongo mongo() throws Exception {
		Mongo mongo = new MongoClient("127.0.0.1");

		return mongo;
	}

}
