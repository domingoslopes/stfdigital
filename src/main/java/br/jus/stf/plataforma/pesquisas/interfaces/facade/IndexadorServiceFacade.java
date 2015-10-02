package br.jus.stf.plataforma.pesquisas.interfaces.facade;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.pesquisas.application.IndexadorApplicationService;
import br.jus.stf.plataforma.pesquisas.domain.model.IndexadorRepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Serviço de fachada que concentra o acesso à indexação
 * 
 * @author Lucas.Rodrigues
 *
 */
@Component
public class IndexadorServiceFacade {
	
	@Autowired
	private IndexadorApplicationService indexadorApplicationService;
	
	@Autowired
	private IndexadorRepository indexadorRepository;
	
	private ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * Cria o índice
	 * 
	 * @param indice
	 * @param configuracao
	 * @throws JsonProcessingException 
	 */
	public void criarIndice(String indice, JsonNode configuracao) throws JsonProcessingException {
		Validate.isTrue(!indexadorRepository.existe(indice));
		JsonNode config = configuracao.findValue("settings");
		JsonNode map = configuracao.findValue("mappings");
		indexadorApplicationService.criarIndice(indice, converterJsonString(config), converterJsonMap(map));
	}
	
	private Map<String, String> converterJsonMap(JsonNode map) throws JsonProcessingException {
		Map<String, String> mapeamentos = new HashMap<String, String>();
		Iterator<String> names = map.fieldNames();
		while(names.hasNext()) {
			String name = names.next();
			mapeamentos.put(name, converterJsonString(map.findValue(name)));
		}
		return mapeamentos;
	}

	/**
	 * Verifica se o índice existe
	 * 
	 * @param indice
	 * @return
	 */
	public boolean existeIndice(String indice) {
		return indexadorRepository.existe(indice);
	}
	
	/**
	 * Indexa um objeto
	 * 
	 * @param indice
	 * @param tipo
	 * @param id
	 * @param objeto
	 * @throws JsonProcessingException
	 */
	public void indexar(String indice, String tipo, String id, JsonNode objeto) throws JsonProcessingException {
		indexadorApplicationService.indexar(indice, tipo, id, converterJsonString(objeto));
	}
	
	/**
	 * Converte o objeto a ser indexado em uma string e encapsula em um outro objeto
	 * 
	 * @param json
	 * @return
	 * @throws JsonProcessingException
	 */
	private String converterJsonString(JsonNode json) throws JsonProcessingException {
		return objectMapper.writeValueAsString(json);
	}
	
}
