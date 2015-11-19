package br.jus.stf.plataforma.acessos.domain.model;

import java.util.Set;

import javax.persistence.AssociationOverride;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.stereotype.Entity;

@javax.persistence.Entity
@Table(name = "USUARIO", schema = "PLATAFORMA", uniqueConstraints = @UniqueConstraint(columnNames = {"SIG_USUARIO"}))
@AttributeOverride(name = "nome", column = @Column(name="NOM_USUARIO", nullable = false))
@AssociationOverride(name = "permissoes",
	joinTable = @JoinTable(name = "PERMISSAO_USUARIO",
					joinColumns = @JoinColumn(name="SEQ_USUARIO")))
public class Usuario extends Principal implements Entity<Usuario, Long> {
	
	@Id
	@Column(name = "SEQ_USUARIO")
	@SequenceGenerator(name = "USUARIOID", sequenceName = "PLATAFORMA.SEQ_USUARIO", allocationSize = 1)
	@GeneratedValue(generator = "USUARIOID", strategy = GenerationType.SEQUENCE)
	private Long sequencial;
	
	@Column(name = "SIG_USUARIO", nullable = false)
	private String login;
	
	@Column(name = "COD_CPF", nullable = false)
	private String cpf;
	
	@Column(name = "COD_OAB")
	private String oab;
	
	@Column(name = "DSC_EMAIL", nullable = false)
	private String email;
	
	@Column(name = "DSC_TELEFONE", nullable = false)
	private String telefone;
	
	public Usuario(final Long sequencial, final String nome, final String login, final String cpf, final String email, final String telefone) {
		super(nome);
		
		Validate.notNull(sequencial, "usuario.sequencial.required");
		Validate.notBlank(login, "usuario.login.required");
		Validate.notBlank(cpf, "usuario.cpf.required");
		Validate.notBlank(email, "usuario.email.required");
		Validate.notBlank(telefone, "usuario.telefone.required");
		
		this.login = login;
		this.cpf = cpf;
		this.email = email;
		this.telefone = telefone;
	}
	
	public Usuario(final Long sequencial, final String nome, final Set<Permissao> permissoes, final String login, final String cpf, final String oab, final String email, final String telefone) {
		this(sequencial, nome, login, cpf, email, telefone);
		
		Validate.notBlank(oab, "usuario.oab.required");
		
		this.oab = oab;
	}
	
	public String login() {
		return login;
	}
	
	public String cpf() {
		return cpf;
	}
	
	public String oab() {
		return oab;
	}
	
	public String email() {
		return email;
	}
	
	public String telefone() {
		return telefone;
	}
	
	@Override
	public Long id() {
		return sequencial;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = prime * result + ((sequencial == null) ? 0 : sequencial.hashCode());
		
		return result;
	}
	
	@Override
	public boolean equals(Object obj){
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
	
		Usuario other = (Usuario) obj;
		return sameIdentityAs(other);
	}

	@Override
	public boolean sameIdentityAs(Usuario other) {
		return other != null && sequencial.equals(other.sequencial);
	}

}
