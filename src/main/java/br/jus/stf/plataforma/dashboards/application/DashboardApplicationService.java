package br.jus.stf.plataforma.dashboards.application;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.jus.stf.plataforma.dashboards.domain.model.Dashboard;
import br.jus.stf.plataforma.dashboards.domain.model.DashboardRepository;

/**
 * @author Lucas.Rodrigues
 *
 */
@Service
@Transactional
public class DashboardApplicationService {

	@Autowired
	private DashboardRepository dashboardRepository;
	
	public List<Dashboard> consultarDashboards() {
		return dashboardRepository.listar();
	}

}
