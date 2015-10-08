package br.jus.stf.processamentoinicial.autuacao.domain.model;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import br.jus.stf.shared.PeticaoId;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:33:25
 */
public interface PeticaoRepository {

	/**
	 * 
	 * @param id
	 * @return peticao
	 */
	public Peticao findOne(PeticaoId id);
	
	/**
	 * 
	 * @param id
	 * @param clazz tipo de retorno
	 * @return peticao
	 */
	public <T> T findOne(PeticaoId id, Class<T> clazz);

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
	
	/**
	 * @para id
	 * @return o tipo de peça
	 */
	public TipoPeca findOneTipoPeca(Long id);
	
	/**
	 * @return a lista de tipos de peças
	 */
	public List<TipoPeca> findAllTipoPeca();
	
	/**
	 * @para id
	 * @return o órgao
	 */
	public Orgao findOneOrgao(Long id);
	
	/**
	 * @return a lista de órgãos
	 */
	public List<Orgao> findAllOrgao();
}