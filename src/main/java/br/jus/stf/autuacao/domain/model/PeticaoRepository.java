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
	public Peticao findOne(PeticaoId id);

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
	
	/**
	 * Gera o próximo id da petição
	 * 
	 * @return o sequencial da petição
	 */
	public PeticaoId nextId();
	
	/**
	 * Recupera o próximo número de petição de acordo com o ano
	 * 
	 * @return o número da petição
	 */
	public Long nextNumero();
	
}