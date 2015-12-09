package br.jus.stf.plataforma.dashboards.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.jus.stf.plataforma.dashboards.domain.model.Dashboard;
import br.jus.stf.plataforma.dashboards.domain.model.DashboardRepository;
import br.jus.stf.plataforma.dashboards.domain.model.DashletRepository;

/**
 * @author Lucas.Rodrigues
 *
 */
@Service
public class DashboardApplicationService {

	@Autowired
	private DashboardRepository dashboardRepository;
	
	@Autowired
	private DashletRepository dashletRepository;
	
	public Dashboard consultarPadrao() {
		Dashboard dashboard = dashboardRepository.consultarPadrao();
		dashboard.dashlets().addAll(dashletRepository.consultarPadrao());
		return dashboard;
	}

}
