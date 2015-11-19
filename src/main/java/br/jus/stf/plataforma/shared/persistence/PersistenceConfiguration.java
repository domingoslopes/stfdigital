package br.jus.stf.plataforma.shared.persistence;

import java.util.Properties;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configuração da persistência
 * 
 * @author Lucas.Rodrigues
 *
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("br.jus.stf")
@EnableSpringDataWebSupport
public class PersistenceConfiguration {
	
	/**
	 * Configura o versionamento do do banco via flyway
	 * 
	 * @param dataSource
	 * @return
	 * @throws Exception
	 */
	@Bean(name = "flyway", initMethod="migrate")
	@DependsOn("dataSource")
	public Flyway flyway(DataSource dataSource) throws Exception {
		Flyway flyway = new Flyway();
		
		flyway.setValidateOnMigrate(false);
		flyway.setDataSource(dataSource);
		
		return flyway;
	}
	
	/**
	 * Propriedades para configuração do Hibernate
	 *  
	 * @return Hibernate properties
	 */
	@Bean(name = "jpaProperties")
	public Properties jpaProperties() {
		Properties prop = new Properties();
		prop.put("hibernate.jdbc.use_scrollable_resultset", "false");
		prop.put("hibernate.hbm2ddl.import_files_sql_extractor", "org.hibernate.tool.hbm2ddl.MultipleLinesSqlCommandExtractor");
		prop.put("hibernate.format_sql", "true");
		prop.put("hibernate.show_sql", "false");
		return prop;
	}

	/**
	 * Configura o EntityManager
	 * 
	 * @param dataSource
	 * @param jpaVendorAdapter
	 * @param jpaProperties
	 * @return entityManagerFactory
	 */
	@Bean
	@DependsOn({"dataSource", "jpaVendorAdapter", "jpaProperties"})
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter, Properties jpaProperties) {
		LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
		lef.setPackagesToScan("br.jus.stf");
		lef.setJpaVendorAdapter(jpaVendorAdapter);
		lef.setDataSource(dataSource);
		lef.setJpaProperties(jpaProperties);
		return lef;
	}

	/**
	 * @return transactionManager
	 */
	@Bean
	public PlatformTransactionManager transactionManager() {
		return new JpaTransactionManager();
	}
	
	/**
	 * @param dataSource
	 * @return jdbcTemplate
	 */
	@Bean(name = "jdbcTemplate")
	@DependsOn("dataSource")
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	/**
	 * @param dataSource
	 * @return namedParameterJdbcTemplate
	 */
	@Bean(name = "namedParameterJdbcTemplate")
	@DependsOn("dataSource")
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
		return new NamedParameterJdbcTemplate(dataSource);
	}
	
}
