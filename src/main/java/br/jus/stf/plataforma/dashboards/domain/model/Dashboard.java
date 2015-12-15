package br.jus.stf.plataforma.dashboards.domain.model;

import java.util.List;

import br.jus.stf.plataforma.shared.security.resource.Resource;
import br.jus.stf.plataforma.shared.security.resource.ResourceType;

/**
 * Entidade Dashboard. É composta por um conjunto de Dashlets.
 * 
 * @author Tomas.Godoi
 *
 */
public class Dashboard extends Resource {

	private String nome;
	private List<Dashlet> dashlets;
	
	public Dashboard(String nome, List<Dashlet> dashlets) {
		super(nome, ResourceType.DASHBOARD);
		
		this.nome = nome;
		this.dashlets = dashlets;
	}
	
	public String nome() {
		return nome;
	}
	
	public List<Dashlet> dashlets() {
		return dashlets;
	}

}
