package br.jus.stf.plataforma.dashboards.domain.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import br.jus.stf.plataforma.shared.security.resource.ResourceImpl;
import br.jus.stf.plataforma.shared.security.resource.ResourceType;
import br.jus.stf.shared.stereotype.Entity;

/**
 * Entidade Dashboard. É composta por um conjunto de Dashlets.
 * 
 * @author Tomas.Godoi
 *
 */
@javax.persistence.Entity
@Table(name = "DASHBOARD", schema = "PLATAFORMA")
public class Dashboard extends ResourceImpl implements Entity<Dashboard, DashboardId> {

	@EmbeddedId
	private DashboardId id;
	
	@Column(name = "NOM_DASHBOARD")
	private String nome;
	
	@ManyToMany
	@JoinTable(name = "DASHBOARD_DASHLET", schema  = "PLATAFORMA",
			joinColumns = @JoinColumn(name = "SEQ_DASHBOARD", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "SEQ_DASHLET", nullable = false, updatable = false))
	private List<Dashlet> dashlets = new ArrayList<Dashlet>();
	
	Dashboard() {
		super(ResourceType.DASHBOARD);
	}
	
	public Dashboard(DashboardId id, String nome) {
		super(id.toString(), ResourceType.DASHBOARD);
		
		this.id = id;
		this.nome = nome;
	}
	
	public DashboardId id() {
		return id;
	}
	
	public String nome() {
		return nome;
	}
	
	public List<Dashlet> dashlets() {
		return dashlets;
	}
	
	@Override
	public String resourceId() {
		return id.toString();
	}

	@Override
	public boolean sameIdentityAs(Dashboard other) {
		return id.equals(id);
	}

}
