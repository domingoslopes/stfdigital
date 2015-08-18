package br.jus.stf.plataforma.workflow.infra.configuration;

import java.io.IOException;

import javax.sql.DataSource;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author Lucas Mariano
 * 
 * @since 1.0.0
 * @since 29.06.2015
 */
@Configuration
public class ActivitiConfiguration {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	@Bean
	public SpringProcessEngineConfiguration processEngineConfiguration() throws IOException {
		SpringProcessEngineConfiguration configuration = new SpringProcessEngineConfiguration();
		configuration.setDataSource(dataSource);
		configuration.setTransactionManager(transactionManager);
		configuration.setDatabaseSchemaUpdate("true");
		configuration.setJobExecutorActivate(false);
		configuration.setDeploymentResources(getResources("classpath*:/processes/*.bpmn20.xml"));
		configuration.setDeploymentMode("single-resource");
		return configuration;
	}
	
	private Resource[] getResources(String resource) throws IOException {
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		return resolver.getResources(resource);
	}
	
	@Bean
	public ProcessEngineFactoryBean processEngine() throws IOException {
		ProcessEngineFactoryBean factoryBean = new ProcessEngineFactoryBean();
		factoryBean.setProcessEngineConfiguration(processEngineConfiguration());
		return factoryBean;
	}
	
	@Bean
	public RuntimeService runtimeService() throws Exception {
		return processEngine().getObject().getRuntimeService();
	}
	
	@Bean
	public RepositoryService repositoryService() throws Exception {
		return processEngine().getObject().getRepositoryService();
	}
	
	@Bean
	public HistoryService historyService() throws Exception {
		return processEngine().getObject().getHistoryService();
	}
	
	@Bean
	public TaskService taskService() throws Exception {
		return processEngine().getObject().getTaskService();
	}
	
	@Bean
	public ManagementService managementService() throws Exception {
		return processEngine().getObject().getManagementService();
	}	

	@Bean
	public IdentityService identityService() throws Exception {
		return processEngine().getObject().getIdentityService();
	}	

}
