package br.jus.stf.generico.domain.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.domain.model.PessoaId;
import br.jus.stf.shared.domain.stereotype.Entity;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:34:02
 */
@javax.persistence.Entity
@Table(name = "PESSOA", schema = "CORPORATIVO")
public class Pessoa implements Entity<Pessoa> {

	@EmbeddedId
	@AttributeOverride(name = "id",
			column = @Column(name = "SEQ_PESSOA", insertable = false, updatable = false))
	private PessoaId id;
	
	@Column(name = "NOM_PESSOA", nullable = false)
	private String nome;

	public Pessoa(final PessoaId id, final String nome){
		Validate.notNull(nome, "pessoa.id.required");
		Validate.notBlank(nome, "pessoa.nome.required");
		
		this.id = id;
		this.nome = nome;
	}

	public PessoaId id(){
		return id;
	}

	public String nome(){
		return nome;
	}
	
	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(final Object o){
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
	
		Pessoa other = (Pessoa) o;
		return sameIdentityAs(other);
	}
	
	@Override
	public boolean sameIdentityAs(final Pessoa other) {
		return other != null && this.id.sameValueAs(other.id);
	}
	
	//Hibernate
	
	Pessoa() {
		
	}
	
}