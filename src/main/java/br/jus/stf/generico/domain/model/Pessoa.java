package br.jus.stf.generico.domain.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostPersist;
import javax.persistence.SequenceGenerator;
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

	@Embedded
	@AttributeOverride(name = "id",
			column = @Column(name = "SEQ_PESSOA", insertable = false, updatable = false))
	private PessoaId pessoaId;
	
	@Column(name = "NOM_PESSOA", nullable = false)
	private String nome;

	public Pessoa(final String nome){
		Validate.notBlank(nome, "pessoa.nome.required");
		
		this.nome = nome;
	}

	public PessoaId id(){
		return pessoaId;
	}

	public String nome(){
		return nome;
	}
	
	@PostPersist
	private void postPersist() {
		pessoaId = new PessoaId(id);
	}
	
	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pessoaId == null) ? 0 : pessoaId.hashCode());
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
		return other != null && this.pessoaId.sameValueAs(other.pessoaId);
	}
	
	//Hibernate
	
	@Id
	@Column(name = "SEQ_PESSOA")
	@SequenceGenerator(name = "PESSOAID", sequenceName = "CORPORATIVO.SEQ_PESSOA", allocationSize = 1)
	@GeneratedValue(generator = "PESSOAID", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	Pessoa() {
		
	}
	
}