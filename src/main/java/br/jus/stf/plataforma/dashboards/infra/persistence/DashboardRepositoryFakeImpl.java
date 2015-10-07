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

@Repository
public class DashboardRepositoryFakeImpl implements DashboardRepository {

	private static final Map<String, Dashboard> mapeamentoPapelDashboard = new HashMap<>();

	static {
		mapeamentoPapelDashboard.put("peticionador", buildDashboardFromDashlets("minhas-peticoes"));
		mapeamentoPapelDashboard.put("preautuador", buildDashboardFromDashlets("peticoes-para-preautuar"));
		mapeamentoPapelDashboard.put("autuador", buildDashboardFromDashlets("grafico-peticoes"));
		mapeamentoPapelDashboard.put("distribuidor",
				buildDashboardFromDashlets("grafico-distribuicao", "grafico-peticoes"));
		mapeamentoPapelDashboard.put("recebedor", buildDashboardFromDashlets("grafico-distribuicao",
				"minhas-peticoes", "grafico-peticoes", "peticoes-para-preautuar"));
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
		return mapeamentoPapelDashboard.get(papel);
	}

}
