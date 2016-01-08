package br.jus.stf.plataforma.documentos.infra.configuration.mongo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

import br.jus.stf.plataforma.shared.settings.AndProfilesCondition;
import br.jus.stf.plataforma.shared.settings.Profiles;

/**
 * @author Rafael.Alencar
 *
 */
@Configuration
@Profile({Profiles.PRODUCAO, Profiles.DOCUMENTO_MONGO})
@Conditional(AndProfilesCondition.class)
public class MongoConfiguration extends AbstractMongoConfiguration {

	@Value(value = "${mongo.ip}")
	private String ipMongo;

	@Bean
	public GridFsTemplate gridFsTemplate() {

		GridFsTemplate gridFsTemplate = null;

		try {
			gridFsTemplate = new GridFsTemplate(mongoDbFactory(), mappingMongoConverter());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return gridFsTemplate;
	}

	@Override
	protected String getDatabaseName() {
		return "test";
	}

	@Override
	@Bean
	public Mongo mongo() throws Exception {
		return new MongoClient(ipMongo);
	}

}
