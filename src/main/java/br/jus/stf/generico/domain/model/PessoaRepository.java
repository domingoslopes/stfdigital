package br.jus.stf.generico.domain.model;

import br.jus.stf.shared.domain.model.PessoaId;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:34:02
 */
public interface PessoaRepository {

	/**
	 * Pesquisa uma pessoa
	 * 
	 * @param pessoaId
	 */
	public Pessoa findOne(PessoaId pessoaId);

	/**
	 * Salva uma pessoa
	 * 
	 * @param pessoa
	 */
	public Pessoa save(Pessoa pessoa);
	
	/**
	 * Recupera o id para uma nova pessoa
	 * 
	 * @return
	 */
	public PessoaId nextId();
	
}