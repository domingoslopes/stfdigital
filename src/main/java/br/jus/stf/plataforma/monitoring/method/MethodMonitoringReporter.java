package br.jus.stf.plataforma.monitoring.method;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;

import br.jus.stf.plataforma.pesquisas.interfaces.IndexadorRestResource;
import br.jus.stf.plataforma.pesquisas.interfaces.command.IndexarCommand;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Componente para indexação das operações de monitoramento de métodos.
 * 
 * @author Tomas.Godoi
 *
 */
@Component
public class MethodMonitoringReporter {

	private static final Logger LOGGER = LoggerFactory.getLogger(MethodMonitoringReporter.class);
	@Autowired
	private IndexadorRestResource indexadorRestResource;

	public void reportDocumentoOperation(MethodCall documentoOperation) {
		IndexarCommand command = new IndexarCommand();
		command.setId(UUID.randomUUID().toString());
		command.setTipo(documentoOperation.getClass().getSimpleName());
		command.setIndice(MethodMonitoringConfiguration.INDICE_MONITORING_METHOD);
		try {
			command.setObjeto(parseToJson(documentoOperation));
			indexadorRestResource.indexar(command, new BeanPropertyBindingResult(command, "indexarCommand"));
		} catch (Exception e) {
			LOGGER.error(String.format("Problemas ao tentar indexar: %s", documentoOperation), e);
		}
	}

	private JsonNode parseToJson(MethodCall documentoOperation) throws IOException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readTree(objectMapper.writeValueAsString(documentoOperation));
	}
}