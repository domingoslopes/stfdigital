package br.jus.stf.plataforma.acessos.domain.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.jus.stf.shared.GrupoId;

@Entity
@Table(name = "GRUPO", schema = "PLATAFORMA", uniqueConstraints = @UniqueConstraint(columnNames = {"NOM_GRUPO", "TIP_GRUPO"}))
public class Grupo implements br.jus.stf.shared.stereotype.Entity<Grupo, GrupoId>, Principal {
	
	@EmbeddedId
	private GrupoId id;
	
	@Column(name = "NOM_GRUPO", nullable = false)
	private String nome;
	
	@Column(name = "TIP_GRUPO", nullable = false)
	@Enumerated(EnumType.STRING)
	private TipoGrupo tipo;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "PERMISSAO_GRUPO", schema = "PLATAFORMA",
		joinColumns = @JoinColumn(name = "SEQ_GRUPO", nullable = false),
		inverseJoinColumns = @JoinColumn(name = "SEQ_PERMISSAO", nullable = false))
	private Set<Permissao> permissoes = new HashSet<Permissao>(0);
	
	Grupo() {
		
	}
	
	public Grupo(final GrupoId id, final String nome, final TipoGrupo tipo) {
		Validate.notNull(id, "grupo.id.required");
		Validate.notBlank(nome, "grupo.nome.required");
		Validate.notNull(tipo, "grupo.tipo.required");
		
		this.id = id;
		this.nome = nome;
		this.tipo = tipo;
	}

	public String nome() {
		return nome;
	}
	
	public TipoGrupo tipo() {
		return tipo;
	}
	
	@Override
	public GrupoId id() {
		return id;
	}
	
	@Override
	public Set<Permissao> permissoes() {
		return Collections.unmodifiableSet(permissoes);
	}

	@Override
	public void atribuirPermissoes(Set<Permissao> permissoes) {
		Validate.notEmpty(permissoes, "grupo.permissoes.required");
		
		this.permissoes.addAll(permissoes);
	}
	
	@Override
	public void removerPermissoes(Set<Permissao> permissoes) {
		Validate.notEmpty(permissoes, "grupo.permissoes.required");
		
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
	
		Grupo other = (Grupo) obj;
		
		return sameIdentityAs(other);
	}

	@Override
	public boolean sameIdentityAs(final Grupo other) {
		return other != null
				&& id.sameValueAs(other.id);
	}

}
