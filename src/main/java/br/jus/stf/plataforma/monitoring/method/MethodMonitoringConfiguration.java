package br.jus.stf.plataforma.monitoring.method;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import br.jus.stf.plataforma.shared.indexacao.IndexadorRestAdapter;
import br.jus.stf.plataforma.shared.util.ResourceFileUtils;

/**
 * Configuração para permitir o monitoramento de chamadas de métodos.
 *
 * Ativa o Spring AOP com sintaxe de AspectJ.
 * 
 * Cria o índice para os dados de monitoramento de chamadas de métodos.
 *
 * @author Tomas.Godoi
 *
 */
@Configuration
@EnableAspectJAutoProxy
public class MethodMonitoringConfiguration {

	public static final String INDICE_MONITORING_METHOD = "monitoring-method";
	private static final String INDICE_MONITORING_METHOD_RESOURCE = "/indices/monitoring/method.json";
	@Autowired
	private IndexadorRestAdapter indexadorRestAdapter;

	/**
	 * O formato do índice é definido no arquivo JSon {@value #INDICE_MONITORING_METHOD_RESOURCE}.
	 *
	 * @throws Exception
	 *             caso ocorra alguma problema ao ler o arquivo JSon ou ao tentar criar o índice no Elasticsearch
	 */
	@PostConstruct
	private void configure() throws Exception {
		String configuracao = ResourceFileUtils.read(INDICE_MONITORING_METHOD_RESOURCE);
		indexadorRestAdapter.criarIndice(INDICE_MONITORING_METHOD, configuracao);
	}
}
