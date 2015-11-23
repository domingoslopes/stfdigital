package br.jus.stf.plataforma.acessos.domain.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.Validate;
import org.hibernate.validator.constraints.Email;

import br.jus.stf.shared.stereotype.Entity;

@javax.persistence.Entity
@Table(name = "USUARIO", schema = "PLATAFORMA", uniqueConstraints = @UniqueConstraint(columnNames = {"SIG_USUARIO"}))
public class Usuario implements Entity<Usuario, Long>, Principal {
	
	@Id
	@Column(name = "SEQ_USUARIO")
	@SequenceGenerator(name = "USUARIOID", sequenceName = "PLATAFORMA.SEQ_USUARIO", allocationSize = 1)
	@GeneratedValue(generator = "USUARIOID", strategy = GenerationType.SEQUENCE)
	private Long sequencial;
	
	@Column(name = "NOM_USUARIO", nullable = false)
	private String nome;
	
	@Column(name = "SIG_USUARIO", nullable = false)
	private String login;
	
	@Column(name = "COD_CPF", nullable = false)
	private String cpf;
	
	@Column(name = "COD_OAB")
	private String oab;
	
	@Email
	@Column(name = "DSC_EMAIL", nullable = false)
	private String email;
	
	@Column(name = "DSC_TELEFONE", nullable = false)
	private String telefone;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinTable(name = "PERMISSAO_USUARIO", schema = "PLATAFORMA",
		joinColumns = @JoinColumn(name = "SEQ_USUARIO", nullable = false),
		inverseJoinColumns = @JoinColumn(name = "SEQ_PERMISSAO", nullable = false))
	private Set<Permissao> permissoes;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinTable(name = "PAPEL_USUARIO", schema = "PLATAFORMA",
		joinColumns = @JoinColumn(name = "SEQ_USUARIO", nullable = false),
		inverseJoinColumns = @JoinColumn(name = "SEQ_PAPEL", nullable = false))
	private Set<Papel> papeis;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinTable(name = "GRUPO_USUARIO", schema = "PLATAFORMA",
		joinColumns = @JoinColumn(name = "SEQ_USUARIO", nullable = false),
		inverseJoinColumns = @JoinColumn(name = "SEQ_GRUPO", nullable = false))
	private Set<Grupo> grupos;
	
	Usuario() {
		
	}
	
	public Usuario(final Long sequencial, final String nome, final String login, final String cpf, final String email, final String telefone) {
		Validate.notNull(sequencial, "usuario.sequencial.required");
		Validate.notBlank(nome, "usuario.nome.required");
		Validate.notBlank(login, "usuario.login.required");
		Validate.notBlank(cpf, "usuario.cpf.required");
		Validate.notBlank(email, "usuario.email.required");
		Validate.notBlank(telefone, "usuario.telefone.required");
		
		this.sequencial = sequencial;
		this.nome = nome;
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
	
	public String tipoPeticionador() {
		return Optional.ofNullable(oab).isPresent() ? "Advogado" : "Cidadão";
	}
	
	public String nome() {
		return nome;
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
	
	public Set<Papel> papeis() {
		return Collections.unmodifiableSet(papeis);
	}
	
	public void atribuirPapeis(final Set<Papel> papeis) {
		Validate.notEmpty(papeis, "usuario.papeis.required");
		
		this.papeis = papeis;
	}
	
	public Set<Grupo> grupos() {
		return Collections.unmodifiableSet(grupos);
	}
	
	public void atribuirGrupos(final Set<Grupo> grupos) {
		Validate.notEmpty(grupos, "usuario.grupos.required");
		
		this.grupos = grupos;
	}
	
	@Override
	public Set<Permissao> permissoes() {
		Set<Permissao> permissoesCompletas = new HashSet<Permissao>();
		
		Optional.ofNullable(papeis).ifPresent(p -> p.forEach(papeis -> permissoesCompletas.addAll(papeis.permissoes())));
		Optional.ofNullable(grupos).ifPresent(g -> g.forEach(grupo -> permissoesCompletas.addAll(grupo.permissoes())));
		Optional.ofNullable(permissoes).ifPresent(p -> permissoesCompletas.addAll(p));
		
		return Collections.unmodifiableSet(permissoesCompletas);
	}
	
	@Override
	public void atribuirPermissoes(final Set<Permissao> permissoes) {
		Validate.notEmpty(permissoes, "usuario.permissoes.required");
		
		this.permissoes = permissoes;
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
		
		return sequencial.equals(other.sequencial);
	}

	@Override
	public boolean sameIdentityAs(Usuario other) {
		return other != null
				&& login.equals(other.login);
	}

}