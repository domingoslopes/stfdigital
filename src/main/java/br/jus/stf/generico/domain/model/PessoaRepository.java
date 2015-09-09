package br.jus.stf.generico.domain.model;

import org.springframework.data.repository.Repository;

import br.jus.stf.shared.domain.model.PessoaId;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:34:02
 */
public interface PessoaRepository extends Repository<Pessoa, PessoaId> {

	/**
	 * 
	 * @param pessoaId
	 */
	public Pessoa findOne(PessoaId pessoaId);

	/**
	 * 
	 * @param pessoa
	 */
	public PessoaId save(Pessoa pessoa);

}