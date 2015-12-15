package br.jus.stf.plataforma.dashboards.domain.model;

import java.util.List;

/**
 * @author Lucas.Rodrigues
 *
 */
public interface DashletRepository {
	
	public List<Dashlet> consultarPadrao();
	
}
