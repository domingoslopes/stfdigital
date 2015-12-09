package br.jus.stf.plataforma.identidades.domain.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Email;
import org.springframework.util.StringUtils;

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
	
	@Column(name = "COD_CPF")
	private String cpf;
	
	@Column(name = "COD_OAB")
	private String oab;
	
	@Email
	@Column(name = "DSC_EMAIL")
	private String email;
	
	@Column(name = "DSC_TELEFONE")
	private String telefone;

	Pessoa() {

	}
	
	public Pessoa(final PessoaId id, final String nome){
		Validate.notNull(id, "pessoa.id.required");
		Validate.notBlank(nome, "pessoa.nome.required");
		
		this.id = id;
		this.nome = nome;
	}
	
	public Pessoa(final PessoaId id, final String nome, final String cpf){
		this(id, nome);
		
		Validate.notBlank(cpf, "pessoa.cpf.required");
				
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
	}
	
	public Pessoa(final PessoaId id, final String nome, final String cpf, final String email, final String telefone){
		this(id, nome);
		
		Validate.notBlank(cpf, "pessoa.cpf.required");
		Validate.notBlank(email, "pessoa.email.required");
		Validate.notBlank(telefone, "pessoa.telefone.required");
		
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.email = email;
		this.telefone = telefone;
	}
	
	public Pessoa(final PessoaId id, final String nome, final String cpf, final String oab, final String email, final String telefone){
		this(id, nome, cpf, email, telefone);
		
		Validate.notBlank(oab, "pessoa.oab.required");
		
		this.oab = oab;
	}

	@Override
	public PessoaId id() {
		return id;
	}

	public String nome() {
		return nome;
	}
	
	public String cpf() {
		return cpf;
	}
	
	public String oab() {
		return oab;
	}
	
	public boolean ehAdvogado() {
		return !StringUtils.isEmpty(oab);
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