package br.jus.stf.plataforma;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
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
	{"br.jus.stf.autuacao.domain.model", "br.jus.stf.generico.domain.model", "br.jus.stf.shared.domain.model"})
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
		dataSource.setUrl("jdbc:h2:~/stfdigital;AUTO_SERVER=TRUE");
		dataSource.setUsername("sa");
		dataSource.setPassword("");
		return dataSource;
	}
    
}
