package br.jus.stf.plataforma.pesquisas.infra.persistence;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Repository;

import br.jus.stf.plataforma.pesquisas.domain.model.IndexadorRepository;

/**
 * @author Lucas.Rodrigues
 *
 */
@Repository
public class IndexadorRepositoryImpl implements IndexadorRepository {

	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	
	@Override
	public void criar(String indice, String configuracao, Map<String, String> mapeamentos) {
		elasticsearchTemplate.createIndex(indice, configuracao);
		mapeamentos.forEach((tipo, map) -> elasticsearchTemplate.putMapping(indice, tipo, map));
		elasticsearchTemplate.refresh(indice, true);
	}

	@Override
	public boolean existe(String indice) {
		return elasticsearchTemplate.indexExists(indice);
	}
	
	@Override
	public void salvar(String indice, String tipo, String id, String objeto) {
		IndexQuery query = new IndexQueryBuilder()
			.withIndexName(indice)
			.withType(tipo)
			.withId(id)
			.withSource(objeto)
			.build();
		elasticsearchTemplate.index(query);
	}

}
