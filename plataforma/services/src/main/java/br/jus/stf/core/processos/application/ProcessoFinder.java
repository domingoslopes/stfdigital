package br.jus.stf.core.processos.application;

import static org.elasticsearch.index.query.QueryBuilders.matchPhraseQuery;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Component;

import br.jus.stf.core.processos.domain.Processo;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 20.02.2016
 */
@Component
public class ProcessoFinder {
	
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
	
	public List<Processo> execute(PesquisarProcessosQuery processoQuery) {
		NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
		
		if (StringUtils.isNotBlank(processoQuery.getProtocolo())) {
			queryBuilder.withQuery(matchPhraseQuery("protocolo", processoQuery.getProtocolo()));
		}
		
		if (StringUtils.isNotBlank(processoQuery.getParte())) {
			queryBuilder.withQuery(matchPhraseQuery("partes", processoQuery.getParte()));
		}
        
        SearchQuery query = queryBuilder.build();
        
        return elasticsearchTemplate.queryForList(query, Processo.class);
	}

}
