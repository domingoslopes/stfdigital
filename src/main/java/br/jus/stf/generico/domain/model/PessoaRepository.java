package br.jus.stf.generico.domain.model;

import br.jus.stf.shared.domain.model.PessoaId;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:34:02
 */
public interface PessoaRepository {

	/**
	 * 
	 * @param pessoaId
	 */
	public Pessoa find(PessoaId pessoaId);

	/**
	 * 
	 * @param pessoa
	 */
	public void store(Pessoa pessoa);

}