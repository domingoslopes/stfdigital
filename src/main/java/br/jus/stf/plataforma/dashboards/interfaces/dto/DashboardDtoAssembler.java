package br.jus.stf.plataforma.dashboards.interfaces.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.dashboards.domain.model.Dashboard;
import br.jus.stf.plataforma.dashboards.domain.model.Dashlet;

/**
 * Componente de conversão para DashboardDto.
 * 
 * @author Tomas.Godoi
 *
 */
@Component
public class DashboardDtoAssembler {

	public DashboardDto toDto(Dashboard dashboard) {
		List<DashletDto> dashlets = dashboard.dashlets().stream()
				.map(dashlet -> toDto(dashlet))
				.collect(Collectors.toList());
		return new DashboardDto(dashboard.nome(), dashlets);
	}
	
	private DashletDto toDto(Dashlet dashlet) {
		return new DashletDto(dashlet.nome(), dashlet.descricao());
	}

}
