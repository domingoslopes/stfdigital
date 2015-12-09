package br.jus.stf.plataforma.dashboards.domain.model;

import java.util.List;

import br.jus.stf.plataforma.shared.security.stereotype.Resource;

/**
 * Entidade Dashboard. É composta por um conjunto de Dashlets.
 * 
 * @author Tomas.Godoi
 *
 */
public class Dashboard extends Resource<Dashboard> {

	private String nome;
	private List<Dashlet> dashlets;
	
	public Dashboard(String nome, List<Dashlet> dashlets) {
		super(nome);
		
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
