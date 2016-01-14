package br.jus.stf.plataforma.dashboards.domain.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.jus.stf.shared.stereotype.Entity;

/**
 * Entidade Dashlet. Representa um componente de exibição de informações para o
 * usuário.
 * 
 * @author Tomas.Godoi
 *
 */
@javax.persistence.Entity
@Table(name = "DASHLET", schema = "PLATAFORMA", uniqueConstraints = @UniqueConstraint(columnNames = {"NOM_DASHLET"}))
public class Dashlet implements Entity<Dashlet, DashletId> {
	
	@EmbeddedId
	private DashletId id;
	
	@Column(name = "NOM_DASHLET")
	private String nome;
	
	@Column(name = "DSC_DASHLET")
	private String descricao;
	
	Dashlet() {
		
	}

	public Dashlet(DashletId id, String nome, String descricao) {
		Validate.notNull(id, "dashlet.id.required");
		Validate.notBlank(nome, "dashlet.nome.required");
		Validate.notBlank(descricao, "dashlet.descricao.required");
		
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
	}
	
	@Override
	public DashletId id() {
		return id;
	}
	
	public String nome() {
		return nome;
	}

	public String descricao() {
		return descricao;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		
		Dashlet other = (Dashlet) obj;
		return sameIdentityAs(other);
	}

	@Override
	public boolean sameIdentityAs(Dashlet other) {
		return id.equals(other.id);
	}

}
