package br.jus.stf.plataforma.dashboards.domain.model;

import java.util.List;

/**
 * Entidade Dashboard. É composta por um conjunto de Dashlets.
 * 
 * @author Tomas.Godoi
 *
 */
public class Dashboard {

	private List<Dashlet> dashlets;

	public Dashboard() {
		
	}
	
	public Dashboard(List<Dashlet> dashlets) {
		this.dashlets = dashlets;
	}
	
	public List<Dashlet> dashlets() {
		return dashlets;
	}

}
