package br.jus.stf.plataforma.dashboards.interfaces.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.dashboards.domain.model.Dashboard;
import br.jus.stf.plataforma.dashboards.domain.model.DashboardRepository;
import br.jus.stf.plataforma.dashboards.interfaces.dto.DashboardDto;
import br.jus.stf.plataforma.dashboards.interfaces.dto.DashboardDtoAssembler;

/**
 * Facade para serviços de Dashboard.
 * 
 * @author Tomas.Godoi
 *
 */
@Component
public class DashboardServiceFacade {

	@Autowired
	private DashboardRepository dashboardRepository;

	@Autowired
	private DashboardDtoAssembler dashboardDtoAssembler;

	public DashboardDto recuperarPadrao() {
		Dashboard dashboard = dashboardRepository.consultarPadrao();
		return dashboardDtoAssembler.toDto(dashboard);
	}

}
