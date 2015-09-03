package br.jus.stf.autuacao.domain.model;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import br.jus.stf.shared.domain.model.PeticaoId;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:33:25
 */
public interface PeticaoRepository {

	/**
	 * 
	 * @param numeroPeticao
	 * @return peticao
	 */
	public Peticao find(PeticaoId numeroPeticao);

	/**
	 * 
	 * @param status
	 * @return lista de peticoes
	 */
	public List<Peticao> findStatus(final PeticaoStatus status);
	
	/**
	 * @param specification
	 * @return lista de peticoes
	 */
	public List<Peticao> find(Specification<Peticao> specification);

	/**
	 * 
	 * @param peticao
	 */
	public void store(Peticao peticao);

	/**
	 * @return o proximo numero de peticao
	 */
	public Long proximoNumero();

}