package br.jus.stf.plataforma.acessos.domain.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.jus.stf.shared.PapelId;

@Entity
@Table(name = "PAPEL", schema = "PLATAFORMA", uniqueConstraints = @UniqueConstraint(columnNames = {"NOM_PAPEL"}))
public class Papel implements br.jus.stf.shared.stereotype.Entity<Papel, PapelId>, Principal {
	
	@EmbeddedId
	private PapelId id;
	
	@Column(name = "NOM_PAPEL", nullable = false)
	private String nome;
	
	@ManyToOne
	@JoinColumn(name = "SEQ_GRUPO")
	private Grupo grupo;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "PERMISSAO_PAPEL", schema = "PLATAFORMA",
		joinColumns = @JoinColumn(name = "SEQ_PAPEL", nullable = false),
		inverseJoinColumns = @JoinColumn(name = "SEQ_PERMISSAO", nullable = false))
	private Set<Permissao> permissoes = new HashSet<Permissao>(0);
	
	Papel() {
		
	}
	
	public Papel(final PapelId id, final String nome) {
		Validate.notNull(id, "papel.id.required");
		Validate.notBlank(nome, "papel.nome.required");
		
		this.id = id;
		this.nome = nome;
	}
	
	public Papel(final PapelId id, final String nome, final Grupo grupo) {
		this(id, nome);
		
		Validate.notNull(grupo, "papel.grupo.required");
		
		this.grupo = grupo;
	}
	
	public String nome() {
		return nome;
	}
	
	public Grupo grupo() {
		return grupo;
	}
	
	@Override
	public PapelId id() {
		return id;
	}

	@Override
	public Set<Permissao> permissoes() {
		return Collections.unmodifiableSet(permissoes);
	}

	@Override
	public void atribuirPermissoes(Set<Permissao> permissoes) {
		Validate.notEmpty(permissoes, "papel.permissoes.required");
		
		this.permissoes.addAll(permissoes);
	}
	
	@Override
	public void removerPermissoes(Set<Permissao> permissoes) {
		Validate.notEmpty(permissoes, "papel.permissoes.required");
		
		this.permissoes.removeAll(permissoes);
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).hashCode();
	}
	
	@Override
	public boolean equals(Object obj){
		
		if (this == obj) {
			return true;
		}
		
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
	
		Papel other = (Papel) obj;
		
		return sameIdentityAs(other);
	}

	@Override
	public boolean sameIdentityAs(final Papel other) {
		return other != null
				&& id.sameValueAs(other.id);
	}

}
