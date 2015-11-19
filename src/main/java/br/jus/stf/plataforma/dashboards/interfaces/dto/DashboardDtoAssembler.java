package br.jus.stf.plataforma.dashboards.interfaces.dto;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.dashboards.domain.model.Dashboard;

/**
 * Componente de conversão para DashboardDto.
 * 
 * @author Tomas.Godoi
 *
 */
@Component
public class DashboardDtoAssembler {

	public DashboardDto toDto(Dashboard dashboard) {
		DashboardDto dashboardDto = new DashboardDto();
		dashboardDto.setDashlets(new ArrayList<String>());
		Optional.ofNullable(dashboard.dashlets()).ifPresent(d -> d.forEach(dashlet -> dashboardDto.getDashlets().add(dashlet.nome())));
		return dashboardDto;
	}

}
