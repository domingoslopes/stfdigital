package br.jus.stf.plataforma.pesquisas.domain.model.pesquisa;

import java.util.List;

import org.springframework.data.domain.Pageable;


/**
 * @author Lucas.Rodrigues
 *
 */
public interface PesquisaAvancadaRepository {
	
	PesquisaAvancadaId nextId();
	
	/**
	 * Consulta uma pesquisa avançada por id
	 * 
	 * @param id
	 * @return
	 */
	PesquisaAvancada findOne(PesquisaAvancadaId id);
	
	/**
	 * Salva uma pesquisa avançada
	 * 
	 * @param pesquisa
	 * @return
	 */
	PesquisaAvancada save(PesquisaAvancada pesquisa);
	
	/**
	 * Lista as minhas pesquisas avançadas salvas
	 * 
	 * @return
	 */
	List<PesquisaAvancada> listarMinhas();
	
	/**
	 * Lista as pesquisas avançadas salvas dos meus papéis
	 * 
	 * @return
	 */
	List<PesquisaAvancada> listarPorMeusPapeis();
	
	/**
	 * Executa diretamente uma pesquisa avançada
	 * 
	 * @param consulta
	 * @param indices
	 * @param paginacao
	 * @return
	 */
	List<Resultado> executar(Pesquisa pesquisa, Pageable paginacao);
	
	/**
	 * Lista os critérios de pesquisa avançada
	 * 
	 * @param indices
	 * @return
	 */
	List<Criterio> listarCriterios(String[] indices);
	
	/**
	 * Deleta uma pesquisa avançada
	 * 
	 * @param pesquisaId
	 */
	void delete(PesquisaAvancadaId pesquisaId);
}
