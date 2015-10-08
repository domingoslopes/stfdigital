package br.jus.stf.plataforma.pesquisas.domain.model;

import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;

/**
 * @author Lucas.Rodrigues
 *
 */
public interface PesquisaRepository {

	/**
	 * Pesquisa nos índices informados aplicando os filtros
	 * 
	 * @param indices
	 * @param filtros
	 * @param campos
	 * @param ordenador
	 * @return o resultado da pesquisa
	 */
	SearchResponse pesquisar(String[] indices, Map<String, String> filtros, String[] campos, String ordenador);	
	
}
