package br.jus.stf.plataforma.identidades.domain.model;

import java.util.List;

import br.jus.stf.shared.PessoaId;

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
	public <T extends Pessoa> T save(Pessoa pessoa);
	
	/**
	 * Recupera o id para uma nova pessoa
	 * 
	 * @return
	 */
	public PessoaId nextId();
	
	/**
	 * Recupera uma lista de pessoas de mesmo nome
	 * 
	 * @return
	 */
	public List<Pessoa> findByNomeContaining(String nome);
	
}