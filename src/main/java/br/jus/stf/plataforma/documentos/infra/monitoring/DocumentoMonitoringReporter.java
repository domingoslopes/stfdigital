package br.jus.stf.plataforma.documentos.infra.monitoring;

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

import br.jus.stf.plataforma.documentos.infra.configuration.DocumentoMonitoringConfiguration;
import br.jus.stf.plataforma.pesquisas.interfaces.IndexadorRestResource;
import br.jus.stf.plataforma.pesquisas.interfaces.command.IndexarCommand;

/**
 * Componente para indexação das operações de monitoramento de documento.
 * 
 * @author Tomas.Godoi
 *
 */
@Component
public class DocumentoMonitoringReporter {

	private static final Logger LOGGER = LoggerFactory.getLogger(DocumentoMonitoringReporter.class);
	@Autowired
	private IndexadorRestResource indexadorRestResource;

	public void reportDocumentoOperation(DocumentoOperation documentoOperation) {
		IndexarCommand command = new IndexarCommand();
		command.setId(UUID.randomUUID().toString());
		command.setTipo(documentoOperation.getClass().getSimpleName());
		command.setIndice(DocumentoMonitoringConfiguration.INDICE_MONITORING_DOCUMENTO);
		try {
			command.setObjeto(parseToJson(documentoOperation));
			indexadorRestResource.indexar(command, new BeanPropertyBindingResult(command, "indexarCommand"));
		} catch (Exception e) {
			LOGGER.error(String.format("Problemas ao tentar indexar: %s", documentoOperation), e);
		}
	}

	private JsonNode parseToJson(DocumentoOperation documentoOperation) throws IOException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readTree(objectMapper.writeValueAsString(documentoOperation));
	}
}