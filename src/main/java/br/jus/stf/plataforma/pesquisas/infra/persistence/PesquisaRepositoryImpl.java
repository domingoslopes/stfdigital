package br.jus.stf.plataforma.pesquisas.infra.persistence;

import static org.elasticsearch.action.search.SearchType.DFS_QUERY_THEN_FETCH;
import static org.elasticsearch.search.sort.SortBuilders.fieldSort;
import static org.elasticsearch.search.sort.SortOrder.ASC;
import static org.elasticsearch.search.sort.SortOrder.DESC;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Repository;

import br.jus.stf.plataforma.pesquisas.domain.model.pesquisa.Pesquisa;
import br.jus.stf.plataforma.pesquisas.domain.model.pesquisa.PesquisaRepository;
import br.jus.stf.plataforma.pesquisas.domain.model.pesquisa.Resultado;

/**
 * @author Lucas.Rodrigues
 *
 */
@Repository
public class PesquisaRepositoryImpl implements PesquisaRepository {
	
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;

	@Override
	public List<Resultado> sugerir(Pesquisa pesquisa) {
		NativeSearchQueryBuilder builder = buildQuery(pesquisa);
		builder.withQuery(buildSuggestionFiltros(pesquisa.filtros()));
		return executeQuery(builder.build());
	}
	
	@Override
	public List<Resultado> pesquisar(Pesquisa pesquisa, Pageable pageable) {
		NativeSearchQueryBuilder builder = buildQuery(pesquisa);
		builder.withQuery(buildFiltros(pesquisa.filtros()));
		Optional.ofNullable(pageable).ifPresent(builder::withPageable);
		return executeQuery(builder.build());
	}
	
	@Override
	public Long count(Pesquisa pesquisa) {
		NativeSearchQueryBuilder builder = buildQuery(pesquisa);
		builder.withQuery(buildFiltros(pesquisa.filtros()));
		return executeCountQuery(builder.build());
	}

	/**
	 * Monta um query com base na pesquisa
	 * 
	 * @param pesquisa
	 * @return uma query builder
	 */
	private NativeSearchQueryBuilder buildQuery(Pesquisa pesquisa) {
		NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
		builder.withFields(pesquisa.campos());
		builder.withTypes(pesquisa.tipos());
		builder.withIndices(pesquisa.indices());
		
		if (pesquisa.campoAgregacao() != null)
			builder.addAggregation(AggregationBuilders.terms("aggs").field(pesquisa.campoAgregacao()).size(20));
		
		builder.withSearchType(DFS_QUERY_THEN_FETCH);
		Map<String, String> ordenadores = pesquisa.ordenadores();
		ordenadores.keySet().forEach(ordenador -> {
			SortOrder sentido = DESC.toString().equalsIgnoreCase(ordenadores.get(ordenador)) ? DESC : ASC;
			builder.withSort(fieldSort(ordenador).order(sentido));
		});
		return builder;
	}

	/**
	 * Constrói uma query com base nos filtros
	 * 
	 * @param filtros
	 * @return uma query baseada nos filtros
	 */
	private BoolQueryBuilder buildFiltros(Map<String, String[]> filtros) {
		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
		filtros.keySet().forEach(campo -> {
			Optional<String[]> filtro = Optional.ofNullable(filtros.get(campo));
			if (filtro.isPresent()) {
				if (filtro.get().length > 1) {
					boolQuery.must(QueryBuilders.termsQuery(campo, filtro.get()));
				} else {
					boolQuery.must(QueryBuilders.matchQuery(campo, filtro.get()[0]).operator(Operator.AND));
				}
			} else {
				boolQuery.must(QueryBuilders.matchQuery(campo, null).operator(Operator.AND));
			}
		});
		return boolQuery;
	}
	
	/**
	 * Constrói uma query por prefixos com base nos filtros
	 * 
	 * @param filtros
	 * @return uma query baseada nos filtros
	 */
	private BoolQueryBuilder buildSuggestionFiltros(Map<String, String[]> filtros) {
		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
		filtros.keySet().forEach(campo -> 
			Arrays.asList(filtros.get(campo)).forEach(filtro -> 
				boolQuery.must(QueryBuilders.prefixQuery(campo, filtro))));
		return boolQuery;
	}
	
	/**
	 * Executa uma query
	 * 
	 * @param query
	 * @return o resultado
	 */
	private List<Resultado> executeQuery(NativeSearchQuery query) {
		
		List<Resultado> resultado = null;
		
		if (query.getAggregations() == null){
			resultado = elasticsearchTemplate.query(query, new ResultadoPesquisa());
		} else {
			resultado = elasticsearchTemplate.query(query, new ResultadoPesquisaAgregada());
		}

		return resultado;
	}
	
	/**
	 * Executa o count de uma query.
	 * 
	 * @param build
	 * @return
	 */
	private Long executeCountQuery(NativeSearchQuery query) {
		return elasticsearchTemplate.count(query);
	}
	
}
