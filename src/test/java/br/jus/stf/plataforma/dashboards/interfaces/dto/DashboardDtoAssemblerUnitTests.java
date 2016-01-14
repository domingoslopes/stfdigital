package br.jus.stf.plataforma.dashboards.interfaces.dto;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.jus.stf.plataforma.dashboards.domain.model.Dashboard;
import br.jus.stf.plataforma.dashboards.domain.model.DashboardId;
import br.jus.stf.plataforma.dashboards.domain.model.Dashlet;
import br.jus.stf.plataforma.dashboards.domain.model.DashletId;

/**
 * Testes unitários para o DashboardDtoAssembler.
 * 
 * @author Tomas.Godoi
 *
 */
public class DashboardDtoAssemblerUnitTests {

	private DashboardDtoAssembler dashboardDtoAssembler;

	@Before
	public void setUp() {
		dashboardDtoAssembler = new DashboardDtoAssembler();
	}

	@Test
	public void converterDashboardToDtoUnicoDashlet() {
		Dashboard dashboard = new Dashboard(new DashboardId(1L), "Dash");
		dashboard.dashlets().add(new Dashlet(new DashletId(1L), "dashlet-01", "Dashlet 01"));
		DashboardDto dto = dashboardDtoAssembler.toDto(dashboard);
		Assert.assertEquals(dto.getDashlets().size(), 1);
	}

	@Test
	public void converterDashboardToDtoVariosDashlets() {
		Dashboard dashboard = new Dashboard(new DashboardId(1L), "Dash");
		dashboard.dashlets().add(new Dashlet(new DashletId(1L), "dashlet-01", "Dashlet 01"));
		dashboard.dashlets().add(new Dashlet(new DashletId(2L), "dashlet-02", "Dashlet 02"));
		DashboardDto dto = dashboardDtoAssembler.toDto(dashboard);
		Assert.assertEquals(dto.getDashlets().size(), 2);
	}

	@Test
	public void converterDashboardToDtoNenhumDashlet() {
		Dashboard dashboard = new Dashboard(new DashboardId(1L), "Dash");
		DashboardDto dto = dashboardDtoAssembler.toDto(dashboard);
		Assert.assertEquals(dto.getDashlets().size(), 0);
	}

}
