package br.jus.stf.plataforma;

import java.util.Properties;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 17.06.2015
 */
@SpringBootApplication
@ComponentScan("br.jus.stf")
@EntityScan(basePackages = 
	{"br.jus.stf.autuacao.domain.model", "br.jus.stf.generico.domain.model", "br.jus.stf.plataforma.domain.model", "br.jus.stf.shared.domain.model"})
public class ApplicationContextInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationContextInitializer.class, args);
    }
    
    @Bean
    public LocalValidatorFactoryBean validator() {
    	return new LocalValidatorFactoryBean();
    }
    
	@Bean(name = "dataSource")
	public DataSource memoryDataSourceServidor() throws Exception {
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		dataSource.setDriverClass(org.h2.Driver.class);
		dataSource.setUrl("jdbc:h2:~/test;MODE=Oracle;AUTO_SERVER=TRUE;DB_CLOSE_DELAY=-1;");
		dataSource.setUsername("sa");
		dataSource.setPassword("");
		return dataSource;
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws Exception {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(memoryDataSourceServidor());

		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		
		Properties properties = new Properties();
	    properties.setProperty("hibernate.hbm2ddl.auto", "validate");
	    properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
	    properties.setProperty("show_sql", "true");
	    properties.setProperty("format_sql", "true");
		em.setJpaProperties(properties);

		return em;
	}
	
	@Bean(name = "flyway", initMethod="migrate")
	public Flyway flywaySchemaVersion() throws Exception {
		Flyway flyway = new Flyway();
		
		flyway.setDataSource(memoryDataSourceServidor());
		flyway.setBaselineOnMigrate(true);
		flyway.setBaselineVersion("0");
		
		if(!flyway.isValidateOnMigrate()) {
			flyway.baseline();
		}
		
		return flyway;
	}
    
}
