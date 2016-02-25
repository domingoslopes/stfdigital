package br.jus.stf.plataforma.pesquisas.infra.persistence;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.springframework.data.elasticsearch.core.ResultsExtractor;

import br.jus.stf.plataforma.pesquisas.domain.model.pesquisa.Resultado;


/**
 * Implementa um ResultsExtractor para extrair os resultados com agregador
 * 
 * @author Lucas.Rodrigues
 *
 */
class ResultadoPesquisaAgregada implements ResultsExtractor<List<Resultado>> {
	
	@Override
	public List<Resultado> extract(SearchResponse response) {
		List<Resultado> documentos = new ArrayList<Resultado>();
					
		try {
			Terms aggs = response.getAggregations().get("aggs"); 
			
			for (Terms.Bucket bucket : aggs.getBuckets()) {
				Map<String, Object> campos = new LinkedHashMap<String, Object>();
				campos.put(bucket.getKey(), bucket.getDocCount());
				
				documentos.add(new Resultado("1", "ValoresAgregados", campos));
			}
		} catch (Exception e) {
			throw e;
		}
		return documentos;
	}
}
