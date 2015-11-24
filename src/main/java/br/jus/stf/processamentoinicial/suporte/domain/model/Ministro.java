package br.jus.stf.processamentoinicial.suporte.domain.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.jus.stf.shared.MinistroId;
import br.jus.stf.shared.stereotype.Entity;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:34:02
 */
@javax.persistence.Entity
@Table(name = "MINISTRO", schema = "AUTUACAO")
public class Ministro implements Entity<Ministro, MinistroId> {

	@EmbeddedId
	private MinistroId codigo;
	
	@Column(name = "NOM_MINISTRO", nullable = false)
	private String nome;

	Ministro() {

	}
	
	public Ministro(final MinistroId codigo, final String nome){
		Validate.notNull(codigo, "ministro.codigo.required");
		Validate.notBlank(nome, "ministro.nome.required");
		
		this.codigo = codigo;
		this.nome = nome;
	}

	@Override
	public MinistroId id(){
		return codigo;
	}

	public String nome(){
		return nome;
	}
	
	@Override
	public int hashCode(){
		return new HashCodeBuilder().append(codigo).hashCode();
	}
	
	@Override
	public boolean equals(final Object o){
		
		if (this == o) {
			return true;
		}
		
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
	
		Ministro other = (Ministro) o;
		return sameIdentityAs(other);
	}
	
	@Override
	public boolean sameIdentityAs(final Ministro other) {
		return other != null && this.codigo.sameValueAs(other.codigo);
	}
	
}