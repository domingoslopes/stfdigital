package br.jus.stf.plataforma.action.interfaces.converters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.minidev.json.JSONObject;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import br.jus.stf.plataforma.action.interfaces.commands.ExecuteActionCommand;
import br.jus.stf.plataforma.action.interfaces.commands.ListActionCommand;

import com.jayway.jsonpath.ReadContext;
import com.jayway.jsonpath.internal.JsonReader;

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
		
		ReadContext reader = new JsonReader().parse(inputMessage.getBody());
		
		String actionId = reader.read("actionId");
		ArrayList<JSONObject> resourcesJson = reader.read("resources");
		List<String> resources = new ArrayList<String>();
		
		resourcesJson.stream()
			.forEach(resource -> resources.add(resource.toJSONString()));
        
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
