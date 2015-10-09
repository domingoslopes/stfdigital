package br.jus.stf.processamentoinicial.autuacao.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;

import br.jus.stf.plataforma.pesquisas.interfaces.IndexadorRestResource;
import br.jus.stf.plataforma.pesquisas.interfaces.command.CriarIndiceCommand;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Lucas.Rodrigues
 *
 */
@Component("indexadorAutuacao")
public class IndexadorRestAdapter {

	@Autowired
	private IndexadorRestResource indexadorRestResource;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	public void criarIndice(String indice, String configuracao) throws Exception {
		JsonNode json = objectMapper.readTree(configuracao);
		CriarIndiceCommand command = new CriarIndiceCommand(indice, json);
		indexadorRestResource.criarIndice(command, new BeanPropertyBindingResult(command, "criarIndiceCommand"));
	}
	
}
