package br.jus.stf.plataforma.monitoring;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.jus.stf.plataforma.pesquisas.interfaces.IndexadorRestResource;
import br.jus.stf.plataforma.pesquisas.interfaces.command.IndexarCommand;

/**
 * Reporta o processamento de uma requisição, com informações de identificação, erros e performance. Registra
 * as informações no Elasticsearch, para posterior pesquisa via Kibana.
 * 
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0.M4
 * @since 16.10.2015
 */
@Component
public class RequestTraceReporter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RequestTraceReporter.class);
	
	@Autowired
	private IndexadorRestResource indexadorRestResource;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	/**
	 * Registra as informações de tracing encapuladas no objeto do tipo {@link RequestTrace} no Elasticsearch. Utiliza a mesma
	 * infraestrutura utiliza por outros mecanismos, como o mecanismo de pesquisa, por exemplo.
	 *
	 * @param requestTrace a {@link RequestTrace} da requisição corrente
	 */
	public void reportRequestTrace(RequestTrace requestTrace) {
		IndexarCommand command = new IndexarCommand();
		command.setId(UUID.randomUUID().toString());
		command.setTipo(requestTrace.getClass().getSimpleName());
		command.setIndice(MonitoringConfiguration.INDICE_UTILIZACAO);
		
        try {
        	command.setObjeto(parseToJson(requestTrace));
			indexadorRestResource.indexar(command, new BeanPropertyBindingResult(command, "indexarCommand"));
		} catch (Exception e) {
			LOGGER.error(String.format("Problemas ao tentar indexar: %s", requestTrace), e);
		}
	}

	/**
	 * Converte um objeto do tipo {@link RequestTrace} para JSon, para armazenamento no Elasticsearch.
	 * 
	 * @param requestTrace a {@link RequestTrace} da requisição corrente
	 * 
	 * @throws IOException em caso de problemas durante a conversão
	 * @throws JsonProcessingException em caso de problemas durante a conversão
	 * 
	 * @return o {@link JsonNode} correspondente
	 */
	private JsonNode parseToJson(RequestTrace requestTrace) throws IOException, JsonProcessingException {
		return objectMapper.reader().readTree(objectMapper.writeValueAsString(requestTrace));
	}

}
