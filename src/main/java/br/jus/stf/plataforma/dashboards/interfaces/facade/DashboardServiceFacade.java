package br.jus.stf.plataforma.dashboards.interfaces.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.dashboards.application.DashboardApplicationService;
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
	private DashboardApplicationService dashboardApplicationService;

	@Autowired
	private DashboardDtoAssembler dashboardDtoAssembler;

	public DashboardDto recuperarPadrao() {
		return dashboardDtoAssembler.toDto(dashboardApplicationService.consultarPadrao());
	}

}
