package br.jus.stf.processamentoinicial.suporte.domain.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.ClasseId;
import br.jus.stf.shared.stereotype.Entity;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:34:02
 */
@javax.persistence.Entity
@Table(name = "CLASSE", schema = "AUTUACAO")
public class Classe implements Entity<Classe, ClasseId> {
	
	@EmbeddedId
	private ClasseId sigla;
	
	@Column(name = "NOM_CLASSE", nullable = false)
	private String nome;

	public Classe(final ClasseId sigla, final String nome){
		Validate.notNull(sigla, "classe.sigla.required");
		Validate.notBlank(nome, "classe.nome.required");
		
		this.sigla = sigla;
		this.nome = nome;
	}

	public ClasseId id(){
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