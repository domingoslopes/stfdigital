package br.jus.stf.plataforma.identidades.domain.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.jus.stf.shared.PessoaId;
import br.jus.stf.shared.stereotype.Entity;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:34:02
 */
@javax.persistence.Entity
@Table(name = "PESSOA", schema = "CORPORATIVO")
public class Pessoa implements Entity<Pessoa, PessoaId> {

	@EmbeddedId
	private PessoaId id;
	
	@Column(name = "NOM_PESSOA", nullable = false)
	private String nome;

	Pessoa() {

	}
	
	public Pessoa(final PessoaId id, final String nome){
		Validate.notNull(id, "pessoa.id.required");
		Validate.notBlank(nome, "pessoa.nome.required");
		
		this.id = id;
		this.nome = nome;
	}

	@Override
	public PessoaId id(){
		return id;
	}

	public String nome(){
		return nome;
	}
	
	@Override
	public int hashCode(){
		return new HashCodeBuilder().append(id).hashCode();
	}
	
	@Override
	public boolean equals(final Object o){
		
		if (this == o) {
			return true;
		}
		
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
	
		Pessoa other = (Pessoa) o;
		
		return sameIdentityAs(other);
	}
	
	@Override
	public boolean sameIdentityAs(final Pessoa other) {
		return other != null && this.id.sameValueAs(other.id);
	}
	
}