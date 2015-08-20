package br.jus.stf.generico.domain.model;

import br.jus.stf.shared.domain.model.MinistroId;
import br.jus.stf.shared.domain.stereotype.ValueObject;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:34:02
 */
public class Ministro implements ValueObject<Ministro> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MinistroId codigo;
	private String nome;

	public Ministro(final MinistroId codigo, final String nome){
		this.codigo = codigo;
		this.nome = nome;
	}

	public MinistroId codigo(){
		return codigo;
	}

	public String nome(){
		return nome;
	}
	
	public boolean sameValueAs(final Ministro other) {
		return other != null && this.codigo.sameValueAs(other.codigo);
	}

}