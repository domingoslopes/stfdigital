package br.jus.stf.plataforma.documentos.infra.configuration.oracle;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import br.jus.stf.plataforma.shared.settings.Profiles;

@Configuration
@Profile(Profiles.DOCUMENTO_ORACLE)
public class OracleDocumentoPersistenceConfiguration {

	@Bean(name = "documentoJdbcTemplate")
	public JdbcTemplate documentoJdbcTemplate() {
		return new JdbcTemplate(documentoOracleDataSource());
	}

	@Bean(name = "dataSourceDocumento")
	public DataSource documentoOracleDataSource() {
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		dataSource.setDriver(new oracle.jdbc.OracleDriver());
		dataSource.setUrl("jdbc:oracle:thin:@10.200.3.129:1521:documento");
		dataSource.setUsername("sys");
		dataSource.setPassword("oracle123");
		return dataSource;
	}

}
