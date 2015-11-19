package br.jus.stf.plataforma.documentos.infra.configuration;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import br.jus.stf.plataforma.shared.settings.Profiles;

import com.mongodb.Mongo;

import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.mongo.tests.MongodForTestsFactory;

/**
 * @author Rafael.Alencar
 */
@Configuration
@Profile(Profiles.DESENVOLVIMENTO)
public class MongoMemoryConfiguration extends AbstractMongoConfiguration {

	private MongodForTestsFactory factory;
	
	@Autowired
	public MongoMemoryConfiguration() throws IOException {
		factory = MongodForTestsFactory.with(Version.Main.PRODUCTION);
	}
	
	@Bean
	public GridFsTemplate gridFsTemplate() throws Exception {
		return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter());
	}

	@Override
	protected String getDatabaseName() {
		return "test";
	}
	
	@Override
	public Mongo mongo() throws Exception {
		return factory.newMongo();
	}
	
}
