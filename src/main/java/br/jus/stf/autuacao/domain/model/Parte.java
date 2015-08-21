package br.jus.stf.autuacao.domain.model;

import br.jus.stf.shared.domain.model.PessoaId;
import br.jus.stf.shared.domain.stereotype.ValueObject;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:33:25
 */
public class Parte implements ValueObject<Parte>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PessoaId pessoaId;
	private TipoPolo polo;

	public Parte(final PessoaId pessoaId, final TipoPolo polo){
		this.pessoaId = pessoaId;
		this.polo = polo;
	}

	public PessoaId pessoaId(){
		return this.pessoaId;
	}

	public TipoPolo polo(){
		return this.polo;
	}

	/**
	 * 
	 * @param other
	 */
	public boolean sameValueAs(final Parte other){
		return other != null && this.pessoaId.sameValueAs(other.pessoaId) && this.polo.sameValueAs(other.polo);
	}

}