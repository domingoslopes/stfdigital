package br.jus.stf.plataforma.dashboards.infra.persistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import br.jus.stf.plataforma.dashboards.domain.model.Dashboard;
import br.jus.stf.plataforma.dashboards.domain.model.DashboardRepository;
import br.jus.stf.plataforma.dashboards.domain.model.Dashlet;

/**
 * Implementação fake temporária do DashboardRepository. Essa implementação fixa
 * Dashboards padrões para os papéis.
 * 
 * @author Tomas.Godoi
 *
 */
@Repository
public class DashboardRepositoryFakeImpl implements DashboardRepository {

	private static final Map<String, Dashboard> mapeamentoPapelDashboard = new HashMap<>();

	static {
		mapeamentoPapelDashboard.put("peticionador", buildDashboardFromDashlets("minhas-tarefas"));
		mapeamentoPapelDashboard.put("preautuador", buildDashboardFromDashlets("minhas-tarefas"));
		mapeamentoPapelDashboard.put("autuador", buildDashboardFromDashlets("minhas-tarefas"));
		mapeamentoPapelDashboard.put("distribuidor", buildDashboardFromDashlets("minhas-tarefas"));
		mapeamentoPapelDashboard.put("recebedor", buildDashboardFromDashlets("minhas-tarefas"));
	}

	private static Dashboard buildDashboardFromDashlets(String... dashletsNames) {
		Dashboard dashboard = new Dashboard();
		List<Dashlet> dashlets = new ArrayList<>();
		Arrays.asList(dashletsNames).forEach(d -> {
			dashlets.add(new Dashlet(d));
		});
		dashboard.setDashlets(dashlets);
		return dashboard;
	}

	@Override
	public Dashboard consultarPadraoDoPapel(String papel) {
		Dashboard dashboard = mapeamentoPapelDashboard.get(papel);
		if (dashboard == null) { // Caso não tenha um dashboard designado para o papel, monta um padrão.
			dashboard = new Dashboard();
			dashboard.setDashlets(Arrays.asList(new Dashlet("minhas-tarefas")));
		}
		return dashboard;
	}

}
