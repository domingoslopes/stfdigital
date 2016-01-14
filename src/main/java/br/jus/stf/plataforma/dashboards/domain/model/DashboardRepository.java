package br.jus.stf.plataforma.dashboards.domain.model;

import java.util.List;

/**
 * Repositório para Dashboard.
 * 
 * @author Tomas.Godoi
 *
 */
public interface DashboardRepository {

	public List<Dashboard> listar();
	
	public Dashboard findOne(DashboardId id);
	
	public DashboardId proximoId();

}
