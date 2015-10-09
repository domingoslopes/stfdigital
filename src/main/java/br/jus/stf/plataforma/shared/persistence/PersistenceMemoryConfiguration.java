package br.jus.stf.plataforma.shared.persistence;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

/**
 * @author Lucas.Rodrigues
 *
 */
@Configuration
//@Profile(Profiles.DESENVOLVIMENTO)
public class PersistenceMemoryConfiguration {
	
	@Bean(name = "dataSource")
	public DataSource dataSource() throws Exception {
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		dataSource.setDriverClass(org.h2.Driver.class);
		dataSource.setUrl("jdbc:h2:mem:stfdigital;MODE=Oracle;DB_CLOSE_DELAY=-1");
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
		return Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8090");	
	}
	
	@Bean(initMethod = "start", destroyMethod = "stop")
	@DependsOn("h2WebServer")
	public Server h2Server(Server h2WebServer) throws SQLException {
		return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "8092");
	}
	
}
