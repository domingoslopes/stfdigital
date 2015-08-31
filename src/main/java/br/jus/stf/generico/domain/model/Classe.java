package br.jus.stf.generico.domain.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Table;

import br.jus.stf.shared.domain.model.ClasseId;
import br.jus.stf.shared.domain.stereotype.Entity;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:34:02
 */
@javax.persistence.Entity
@Table(name = "CLASSE")
public class Classe implements Entity<Classe> {
	
	@EmbeddedId
	private ClasseId sigla;
	
	@Column(name = "DSC_NOME")
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
	
	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sigla == null) ? 0 : sigla.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(final Object o){
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
	
		Classe other = (Classe) o;
		return sameIdentityAs(other);
	}
	
	@Override
	public boolean sameIdentityAs(final Classe other) {
		return other != null && this.sigla.sameValueAs(other.sigla);
	}
	
	//Hibernate

	Classe() {
		
	}
}