package br.jus.stf.plataforma.dashboards.interfaces.facade;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.jus.stf.plataforma.dashboards.domain.model.Dashboard;
import br.jus.stf.plataforma.dashboards.domain.model.DashboardRepository;
import br.jus.stf.plataforma.dashboards.domain.model.Dashlet;
import br.jus.stf.plataforma.dashboards.interfaces.dto.DashboardDto;
import br.jus.stf.plataforma.dashboards.interfaces.dto.DashboardDtoAssembler;
import br.jus.stf.plataforma.dashboards.interfaces.facade.DashboardServiceFacade;

/**
 * Testes unitários para o DashboardServiceFacade.
 * 
 * @author Tomas.Godoi
 *
 */
public class DashboardServiceFacadeUnitTests {

	@Mock
	private DashboardRepository dashboardRepository;

	@Mock(answer = Answers.CALLS_REAL_METHODS)
	private DashboardDtoAssembler dashboardDtoAssembler;

	@InjectMocks
	private DashboardServiceFacade dashboardServiceFacade;

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void recuperarPadrao() throws Exception {
		Dashboard fakeDashboard = new Dashboard(Arrays.asList(new Dashlet("dashlet-01"), new Dashlet("dashlet-02")));
		Mockito.when(dashboardRepository.consultarPadraoDoPapel("papel")).thenReturn(fakeDashboard);

		DashboardDto dashboard = dashboardServiceFacade.recuperarPadrao("papel");
		Assert.assertArrayEquals(new String[] { "dashlet-01", "dashlet-02" }, dashboard.getDashlets().toArray());
	}

}
