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

	private String nome;
	private List<DashletDto> dashlets;
	
	public DashboardDto(String nome, List<DashletDto> dashlets) {
		this.nome = nome;
		this.dashlets = dashlets;
	}
	
	public String getNome() {
		return nome;
	}

	public List<DashletDto> getDashlets() {
		return dashlets;
	}

}
