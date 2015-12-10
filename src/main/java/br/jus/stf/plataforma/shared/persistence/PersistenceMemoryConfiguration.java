package br.jus.stf.plataforma.shared.persistence;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import br.jus.stf.plataforma.shared.settings.Profiles;

/**
 * @author Lucas.Rodrigues
 *
 */
@Configuration
//@Profile(Profiles.DESENVOLVIMENTO)
public class PersistenceMemoryConfiguration {
	
	@Value("${H2.web.port}")
	private String webPort;
	
	@Value("${H2.tcp.port}")
	private String tcpPort;
	
	@Profile("!" + Profiles.KEEP_DATA)
	@Bean(name = "dataSource")
	public DataSource dataSourceMemory() throws Exception {
		return dataSourceH2("jdbc:h2:mem:stfdigital;MODE=Oracle;DB_CLOSE_DELAY=-1");
	}
	
	@Profile(Profiles.KEEP_DATA)
	@Bean(name = "dataSource")
	public DataSource dataSourceDisk() throws Exception {
		return dataSourceH2("jdbc:h2:~/stfdigital-data/stfdigital;MODE=Oracle;AUTO_SERVER=TRUE;DB_CLOSE_DELAY=-1");
	}

	private DataSource dataSourceH2(String connectionUrl) throws Exception {
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		dataSource.setDriverClass(org.h2.Driver.class);
		dataSource.setUrl(connectionUrl);
		dataSource.setUsername("sa");
		dataSource.setPassword("");
		return dataSource;
	}
	
	/**
	 * Adaptador do Hibernate para JPA com configurações para H2.
	 * 
	 * @return Hibernate adapter
	 */
	@Bean(name = "jpaVendorAdapter")
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setDatabase(Database.H2);
		hibernateJpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.H2Dialect");
		hibernateJpaVendorAdapter.setShowSql(false);
		return hibernateJpaVendorAdapter;
	}
	
	@Bean(initMethod = "start", destroyMethod = "stop")
	public Server h2WebServer() throws SQLException {
		return Server.createWebServer("-web", "-webAllowOthers", "-webPort", webPort);	
	}
	
	@Bean(initMethod = "start", destroyMethod = "stop")
	public Server h2Server() throws SQLException {
		return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", tcpPort);
	}
	
}
