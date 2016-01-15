package br.jus.stf.plataforma.documentos.infra.configuration;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import br.jus.stf.plataforma.shared.indexacao.IndexadorRestAdapter;
import br.jus.stf.plataforma.shared.util.ResourceFileUtils;

/**
 * Configuração para permitir o monitoramento do armazenamento e recuperação de documentos.
 *
 * Ativa o Spring AOP com sintaxe de AspectJ.
 * 
 * Cria o índice para os dados de monitoramento de documento.
 *
 * @author Tomas.Godoi
 *
 */
@Configuration
@EnableAspectJAutoProxy
public class DocumentoMonitoringConfiguration {

	public static final String INDICE_MONITORING_DOCUMENTO = "monitoring-documento";
	private static final String INDICE_MONITORING_DOCUMENTO_RESOURCE = "/indices/monitoring/documento.json";
	@Autowired
	private IndexadorRestAdapter indexadorRestAdapter;

	/**
	 * O formato do índice é definido no arquivo JSon {@value #INDICE_MONITORING_DOCUMENTO_RESOURCE}.
	 *
	 * @throws Exception
	 *             caso ocorra alguma problema ao ler o arquivo JSon ou ao tentar criar o índice no Elasticsearch
	 */
	@PostConstruct
	private void configure() throws Exception {
		String configuracao = ResourceFileUtils.read(INDICE_MONITORING_DOCUMENTO_RESOURCE);
		indexadorRestAdapter.criarIndice(INDICE_MONITORING_DOCUMENTO, configuracao);
	}
}
