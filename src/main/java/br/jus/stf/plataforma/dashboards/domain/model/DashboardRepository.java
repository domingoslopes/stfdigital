package br.jus.stf.plataforma.dashboards.domain.model;

/**
 * Repositório para Dashboard.
 * 
 * @author Tomas.Godoi
 *
 */
public interface DashboardRepository {

	public Dashboard consultarPadraoDoPapel(String papel);

}
