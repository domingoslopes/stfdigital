package br.jus.stf.plataforma.pesquisas.interfaces.facade;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.jus.stf.plataforma.pesquisas.application.IndexadorApplicationService;
import br.jus.stf.plataforma.pesquisas.domain.model.indexacao.Indice;

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
	
	private ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * Cria o índice
	 * 
	 * @param indice
	 * @param configuracao
	 * @throws JsonProcessingException 
	 */
	public void criarIndice(String indice, JsonNode configuracao) throws JsonProcessingException {
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
	 * Indexa um objeto
	 * 
	 * @param id 
	 * @param tipo
	 * @param indice 
	 * @param objeto
	 * @throws JsonProcessingException
	 */
	public void indexar(String id, String tipo, String indice, JsonNode objeto) throws JsonProcessingException {
		Indice index = new Indice(indice); 
		indexadorApplicationService.indexar(id, tipo, index, converterJsonString(objeto));
	}
	
	/**
	 * Atualiza um objeto
	 * 
	 * @param id
	 * @param indice
	 * @param objeto
	 * @throws JsonProcessingException 
	 */
	public void atualizar(String id, String tipo, String indice, JsonNode objeto) throws JsonProcessingException {
		Indice index = new Indice(indice); 
		indexadorApplicationService.atualizar(id, tipo, index, converterJsonString(objeto));
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

	public void atualizarColecao(String id, String tipo, String indice, String campoColecao, String expressaoId,
			Object idItem, JsonNode objeto) throws JsonProcessingException {
		Indice index = new Indice(indice); 
		indexadorApplicationService.atualizarColecao(id, tipo, index, campoColecao, expressaoId, idItem, converterJsonString(objeto));
	}
	
}
