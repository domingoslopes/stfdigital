package br.jus.stf.plataforma.dashboards.infra.persistence;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.jus.stf.plataforma.dashboards.domain.model.Dashboard;
import br.jus.stf.plataforma.dashboards.domain.model.DashboardRepository;
import br.jus.stf.plataforma.dashboards.domain.model.Dashlet;
import br.jus.stf.plataforma.shared.security.AcessosRestAdapter;

/**
 * Implementação fake temporária do DashboardRepository. Essa implementação fixa
 * Dashboards padrões para os papéis.
 * 
 * @author Tomas.Godoi
 *
 */
@Repository
public class DashboardRepositoryImpl implements DashboardRepository {
	
	private static final String DASHBOARD_PADRAO = "Dashboard";
	
	@Autowired
	private AcessosRestAdapter acessosRestAdapter;
	
	@Override
	public Dashboard consultarPadrao() {
		return new Dashboard(DASHBOARD_PADRAO, new ArrayList<Dashlet>());
	}

}
