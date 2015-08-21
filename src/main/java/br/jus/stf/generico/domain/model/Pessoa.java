package br.jus.stf.generico.domain.model;

import br.jus.stf.shared.domain.model.PessoaId;
import br.jus.stf.shared.domain.stereotype.Entity;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:34:02
 */
public class Pessoa implements Entity<Pessoa> {

	private PessoaId pessoaId;
	private String nome;

	public Pessoa(final PessoaId pessoaId, final String nome){
		this.pessoaId = pessoaId;
		this.nome = nome;
	}

	public PessoaId pessoaId(){
		return pessoaId;
	}

	public String nome(){
		return nome;
	}
	
	public boolean sameIdentityAs(final Pessoa other) {
		return other != null && this.pessoaId.sameValueAs(other.pessoaId);
	}

}