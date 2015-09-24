package br.jus.stf.autuacao.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;

import br.jus.stf.pesquisa.interfaces.IndexadorRestResource;
import br.jus.stf.pesquisa.interfaces.command.CriarIndiceCommand;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Lucas.Rodrigues
 *
 */
@Component
public class IndexadorRestAdapter {

	@Autowired
	private IndexadorRestResource indexadorRestResource;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	public void criarIndice(String indice, String configuracao) throws Exception {
		JsonNode json = objectMapper.readTree(configuracao);
		CriarIndiceCommand command = new CriarIndiceCommand(indice, json);
		indexadorRestResource.criarIndice(command, new BeanPropertyBindingResult(command, "criarIndiceCommand"));
	}
	
	public boolean existeIndice(String indice) {
		return indexadorRestResource.existeIndice(indice);
	}
	
}
