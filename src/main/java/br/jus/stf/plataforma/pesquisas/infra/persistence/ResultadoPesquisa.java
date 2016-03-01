package br.jus.stf.plataforma.pesquisas.infra.persistence;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.springframework.data.elasticsearch.core.ResultsExtractor;

import br.jus.stf.plataforma.pesquisas.domain.model.pesquisa.Resultado;

import com.fasterxml.jackson.core.JsonProcessingException;


/**
 * Implementa um ResultsExtractor para extrair os resultados
 * 
 * @author Lucas.Rodrigues
 *
 */
class ResultadoPesquisa implements ResultsExtractor<List<Resultado>> {
	
	@Override
	public List<Resultado> extract(SearchResponse response) {
		List<Resultado> documentos = new ArrayList<Resultado>();
		try {
			for (SearchHit hit : response.getHits()) {
				documentos.add(coletarResultado(hit));
			}
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		return documentos;
	}
	
	/**
	 * Coleta um resultado a partir de um item encontrado na pesquisa
	 * 
	 * @param hit
	 * @return
	 * @throws JsonProcessingException 
	 */
	private Resultado coletarResultado(SearchHit hit) throws JsonProcessingException {
		Map<String, Object> source = hit.isSourceEmpty() ? coletarCampos(hit) : hit.getSource();
		return new Resultado(hit.getId(), hit.getType(), source);
	}
	
	/**
	 * Coleta um mapa com nome dos campos como chave
	 * 
	 * @param hit
	 * @return um mapa dos campos
	 */
	private Map<String, Object> coletarCampos(final SearchHit hit) {
		Map<String, Object> campos = new LinkedHashMap<String, Object>();
		hit.fields().keySet()
			.forEach(campo -> campos.put(campo, hit.field(campo).getValue()));
		return campos;
	}
}