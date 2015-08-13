package br.jus.stf.plataforma.action.interfaces.converters;

import java.io.IOException;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import br.jus.stf.plataforma.action.interfaces.commands.ListActionCommand;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

/**
 * @author Lucas.Rodrigues
 *
 */
public class ListActionCommandConverter extends AbstractHttpMessageConverter<ListActionCommand> {

	public ListActionCommandConverter() {
		super(MediaType.APPLICATION_JSON);
	}
	
	@Override
	protected boolean supports(Class<?> clazz) {
		return ListActionCommand.class.equals(clazz);
	}

	@Override
	protected ListActionCommand readInternal(
			Class<? extends ListActionCommand> clazz,
			HttpInputMessage inputMessage) throws IOException,
			HttpMessageNotReadableException {
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(inputMessage.getBody());
		
		JsonNode context = node.findValue("context");
		JsonNode resourcesType = node.findValue("resourcesType");
        ArrayNode resources = (ArrayNode) node.findValue("resources");
        
        ListActionCommand listActionCommand = new ListActionCommand();
        listActionCommand.setContext((context == null) ? null : context.asText());
        listActionCommand.setResourcesType(resourcesType.asText());
        listActionCommand.setResources(resources);
        
        return listActionCommand;
    }

	@Override
	protected void writeInternal(ListActionCommand t,
			HttpOutputMessage outputMessage) throws IOException,
			HttpMessageNotWritableException {
		
	}

}
