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
	@JoinTable(name = "GRUPO_RECURSO", schema = "PLATAFORMA",
		joinColumns = @JoinColumn(name = "SEQ_GRUPO", nullable = false),
		inverseJoinColumns = @JoinColumn(name = "SEQ_RECURSO", nullable = false))
	private Set<Recurso> recursos = new HashSet<Recurso>(0);
	
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
	public Set<Recurso> recursos() {
		return Collections.unmodifiableSet(recursos);
	}

	@Override
	public void atribuirRecursos(Set<Recurso> recursos) {
		Validate.notEmpty(recursos, "grupo.recursos.required");
		
		this.recursos.addAll(recursos);
	}
	
	@Override
	public void removerRecursos(Set<Recurso> recursos) {
		Validate.notEmpty(recursos, "grupo.recursos.required");
		
		this.recursos.removeAll(recursos);
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
