package br.jus.stf.plataforma.dashboards.interfaces.dto;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.jus.stf.plataforma.dashboards.domain.model.Dashboard;
import br.jus.stf.plataforma.dashboards.domain.model.Dashlet;

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
		Dashboard dashboard = new Dashboard(Arrays.asList(new Dashlet("dashlet-01")));
		DashboardDto dto = dashboardDtoAssembler.toDto(dashboard);
		Assert.assertArrayEquals(new String[] { "dashlet-01" }, dto.getDashlets().toArray());
	}

	@Test
	public void converterDashboardToDtoVariosDashlets() {
		Dashboard dashboard = new Dashboard(Arrays.asList(new Dashlet("dashlet-01"), new Dashlet("dashlet-02"), new Dashlet("dashlet-03")));
		DashboardDto dto = dashboardDtoAssembler.toDto(dashboard);
		Assert.assertArrayEquals(new String[] { "dashlet-01", "dashlet-02", "dashlet-03" },
				dto.getDashlets().toArray());
	}

	@Test
	public void converterDashboardToDtoNenhumDashlet() {
		Dashboard dashboard = new Dashboard();
		DashboardDto dto = dashboardDtoAssembler.toDto(dashboard);
		Assert.assertEquals(0, dto.getDashlets().size());
	}

}
