package br.jus.stf.plataforma.pesquisas.infra.persistence;

import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.MatchQueryBuilder.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import br.jus.stf.plataforma.pesquisas.domain.model.PesquisaRepository;

/**
 * @author Lucas.Rodrigues
 *
 */
@Repository
public class PesquisaRepositoryImpl implements PesquisaRepository {
	
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;

	@Override
	public SearchResponse pesquisar(String[] indices, Map<String, String> filtros, String[] campos, String ordenador) {
		
		NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
		if (indices != null && indices.length > 0) {
			builder.withIndices(indices);
		}
		if (campos != null && campos.length > 0){
			builder.withFields(campos);
		}
		if (!StringUtils.isEmpty(ordenador)) {
			builder.withSort(SortBuilders.fieldSort(ordenador).order(SortOrder.ASC));
		}
		filtros.keySet().forEach(campo -> builder.withQuery(
				QueryBuilders.matchQuery(campo, filtros.get(campo)).operator(Operator.AND)));
		NativeSearchQuery query = builder.withSearchType(SearchType.DFS_QUERY_THEN_FETCH).build();
		return elasticsearchTemplate.query(query, new ResultadoPesquisa());
	}
	
	/**
	 * Extrai o resultado da pesquisa
	 */
	private final class ResultadoPesquisa implements ResultsExtractor<SearchResponse> {
		@Override
		public SearchResponse extract(SearchResponse response) {
			return response;
		}
	}
	
}
