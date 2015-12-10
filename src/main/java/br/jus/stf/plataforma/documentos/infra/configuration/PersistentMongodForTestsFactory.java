package br.jus.stf.plataforma.documentos.infra.configuration;

import java.io.IOException;
import java.net.UnknownHostException;

import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Storage;
import de.flapdoodle.embed.mongo.distribution.IFeatureAwareVersion;
import de.flapdoodle.embed.mongo.tests.MongodForTestsFactory;

public class PersistentMongodForTestsFactory extends MongodForTestsFactory {

	public PersistentMongodForTestsFactory() throws IOException {
		super();
	}

	@Override
	protected IMongodConfig newMongodConfig(IFeatureAwareVersion version) throws UnknownHostException, IOException {
		String userDir = System.getProperty("user.home");
		Storage replication = new Storage(userDir + "/stfdigital-data/mongodb", null, 0);

		return new MongodConfigBuilder().version(version).replication(replication).build();
	}

}
