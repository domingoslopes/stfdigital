package br.jus.stf.generico.domain.model;

import br.jus.stf.shared.domain.model.ClasseId;
import br.jus.stf.shared.domain.stereotype.ValueObject;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:34:02
 */
public class Classe implements ValueObject<Classe> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ClasseId sigla;
	private String nome;

	public Classe(final ClasseId sigla, final String nome){
		this.sigla = sigla;
		this.nome = nome;
	}

	public ClasseId sigla(){
		return this.sigla;
	}

	public String nome(){
		return this.nome;
	}
	
	public boolean sameValueAs(final Classe other) {
		return other != null && this.sigla.sameValueAs(other.sigla);
	}

}