package br.jus.stf.plataforma.dashboards.interfaces.dto;

import java.util.List;

/**
 * Dto para Dashboard, que é composto por vários dashlets, identificados aqui
 * pelo seu nome.
 * 
 * @author Tomas.Godoi
 *
 */
public class DashboardDto {

	private List<String> dashlets;

	public List<String> getDashlets() {
		return dashlets;
	}

	public void setDashlets(List<String> dashlets) {
		this.dashlets = dashlets;
	}

}
