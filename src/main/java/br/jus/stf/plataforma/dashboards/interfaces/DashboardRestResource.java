package br.jus.stf.plataforma.dashboards.interfaces;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.plataforma.dashboards.application.DashboardApplicationService;
import br.jus.stf.plataforma.dashboards.interfaces.dto.DashboardDto;
import br.jus.stf.plataforma.dashboards.interfaces.dto.DashboardDtoAssembler;

import com.wordnik.swagger.annotations.ApiOperation;

/**
 * Api REST para recuperar dashboards.
 * 
 * @author Tomas.Godoi
 */
@RestController
@RequestMapping("/api/dashboards")
public class DashboardRestResource {

	@Autowired
	private DashboardApplicationService dashboardApplicationService;
	
	@Autowired
	private DashboardDtoAssembler dashboardDtoAssembler;

	@ApiOperation("Recupera o dashboard padrão para o papel recebido")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<DashboardDto> recuperarPadrao() {
		return dashboardApplicationService.consultarDashboards().stream()
				.map(dashboard -> dashboardDtoAssembler.toDto(dashboard))
				.collect(Collectors.toList());
	}

}
