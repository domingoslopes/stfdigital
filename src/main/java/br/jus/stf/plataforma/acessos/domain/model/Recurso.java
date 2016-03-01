package br.jus.stf.plataforma.acessos.domain.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.jus.stf.plataforma.shared.security.resource.ResourceType;
import br.jus.stf.shared.RecursoId;
import br.jus.stf.shared.stereotype.Entity;

@javax.persistence.Entity
@Table(name = "RECURSO", schema = "PLATAFORMA", uniqueConstraints = @UniqueConstraint(columnNames = {"NOM_RECURSO", "TIP_RECURSO"}))
public class Recurso implements Entity<Recurso, RecursoId> {
	
	@EmbeddedId
	private RecursoId id;
	
	@Column(name = "NOM_RECURSO", nullable = false)
	private String nome;
	
	@Column(name = "TIP_RECURSO", nullable = false)
	@Enumerated(EnumType.STRING)
	private ResourceType tipo;
	
	Recurso() {
		
	}
	
	public Recurso(final RecursoId id, final String nome, final ResourceType tipo) {
		Validate.notNull(id, "recurso.id.required");
		Validate.notBlank(nome, "recurso.nome.required");
		Validate.notNull(tipo, "recurso.tipo.required");
		
		this.id = id;
		this.nome = nome;
		this.tipo = tipo;
	}
	
	@Override
	public RecursoId id() {
		return id;
	}
	
	public String nome() {
		return nome;
	}
	
	public ResourceType tipo() {
		return tipo;
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
	
		Recurso other = (Recurso) obj;
		
		return id.equals(other.id);
	}
	
	@Override
	public boolean sameIdentityAs(final Recurso other) {
		return other != null && id.equals(other.id);
	}

}
