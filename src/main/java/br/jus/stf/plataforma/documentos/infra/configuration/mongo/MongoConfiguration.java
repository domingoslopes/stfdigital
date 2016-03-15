package br.jus.stf.plataforma.documentos.infra.configuration.mongo;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import br.jus.stf.plataforma.shared.settings.AndProfilesCondition;
import br.jus.stf.plataforma.shared.settings.Profiles;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

/**
 * @author Rafael.Alencar
 *
 */
@Configuration
@Profile({Profiles.MONGO_SERVER, Profiles.DOCUMENTO_MONGO})
@Conditional(AndProfilesCondition.class)
public class MongoConfiguration extends AbstractMongoConfiguration {

	@Value("${mongo.ip}")
	private String ipMongo;
	@Value("${mongo.databaseName}")
	private String databaseName;
	@Value("${mongo.username}")
	private String username;
	@Value("${mongo.password}")
	private char[] password;
	@Value("${mongo.authDatabaseName}")
	private String authDatabaseName;

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
	
	private List<MongoCredential> mongoCredentials() {
		return Arrays.asList(MongoCredential.createMongoCRCredential(username, authDatabaseName, password));
	}
	
	@Override
	protected String getDatabaseName() {
		return databaseName;
	}

	@Override
	@Bean
	public Mongo mongo() throws Exception {
		return new MongoClient(new ServerAddress(ipMongo), mongoCredentials());
	}

}
