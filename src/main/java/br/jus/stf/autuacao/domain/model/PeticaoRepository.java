package br.jus.stf.autuacao.domain.model;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.Repository;

import br.jus.stf.shared.domain.model.PeticaoId;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:33:25
 */
public interface PeticaoRepository extends Repository<Peticao, PeticaoId> {

	/**
	 * 
	 * @param numeroPeticao
	 * @return peticao
	 */
	public Peticao findOne(PeticaoId numeroPeticao);

	/**
	 * @param specification
	 * @return lista de peticoes
	 */
	public List<Peticao> findAll(Specification<Peticao> specification);

	/**
	 * 
	 * @param peticao
	 * @return o id da peticao
	 */
	public Peticao save(Peticao peticao);

}