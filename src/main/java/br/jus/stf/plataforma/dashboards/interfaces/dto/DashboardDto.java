package br.jus.stf.plataforma.dashboards.interfaces.dto;

import java.util.List;

public class DashboardDto {

	private List<String> dashlets;

	public List<String> getDashlets() {
		return dashlets;
	}

	public void setDashlets(List<String> dashlets) {
		this.dashlets = dashlets;
	}

}
