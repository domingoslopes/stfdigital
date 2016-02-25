package br.jus.stf.plataforma.pesquisas.domain.model.pesquisa;

import java.util.List;


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
	 * @param tamanho 
	 * @param pagina 
	 * @return
	 */
	List<Resultado> executar(String consulta, Integer pagina, Integer tamanho);	
}
