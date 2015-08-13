package br.jus.stf.plataforma.action.interfaces.converters;

import java.io.IOException;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import br.jus.stf.plataforma.action.interfaces.commands.ExecuteActionCommand;
import br.jus.stf.plataforma.action.interfaces.commands.ListActionCommand;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

/**
 * @author Lucas.Rodrigues
 *
 */
public class ExecuteActionCommandConverter extends AbstractHttpMessageConverter<ExecuteActionCommand> {

	public ExecuteActionCommandConverter() {
		super(MediaType.APPLICATION_JSON);
	}
	
	@Override
	protected boolean supports(Class<?> clazz) {
		return ListActionCommand.class.equals(clazz);
	}

	@Override
	protected ExecuteActionCommand readInternal(
			Class<? extends ExecuteActionCommand> clazz,
			HttpInputMessage inputMessage) throws IOException,
			HttpMessageNotReadableException {
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(inputMessage.getBody());
		
        String actionId = node.findValue("actionId").asText();
        ArrayNode resources = (ArrayNode) node.findValue("resources");
        
        ExecuteActionCommand executeActionCommand = new ExecuteActionCommand();
        executeActionCommand.setActionId(actionId);
        executeActionCommand.setResources(resources);
        
        return executeActionCommand;
    }

	@Override
	protected void writeInternal(ExecuteActionCommand t,
			HttpOutputMessage outputMessage) throws IOException,
			HttpMessageNotWritableException {
		
	}

}
