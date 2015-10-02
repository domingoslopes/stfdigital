package br.jus.stf.plataforma.pesquisas.interfaces.dto;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.springframework.stereotype.Component;

/**
 * @author Lucas.Rodrigues
 *
 */
@Component
public class PesquisaDtoAssembler {

	public List<ResultadoPesquisaDto> toDto(SearchResponse resultado) {
	    List<ResultadoPesquisaDto> dtos = new ArrayList<ResultadoPesquisaDto>();    
	    for (SearchHit hit : resultado.getHits()) {
	    	Object source = hit.isSourceEmpty() ? coletarCampos(hit) : hit.getSource();
	    	ResultadoPesquisaDto dto = new ResultadoPesquisaDto(hit.getId(), hit.getType(), source);
	        dtos.add(dto);
	    }
	    return dtos;
	}

	/**
	 * @param hit
	 * @return
	 */
	private Map<String, Object> coletarCampos(SearchHit hit) {
		Map<String, Object> fieldSource = new LinkedHashMap<String, Object>();
		hit.fields().keySet().forEach(field -> fieldSource.put(field, hit.field(field).getValue()));
		return fieldSource;
	}
	
}
