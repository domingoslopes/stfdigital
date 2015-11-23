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

	private static final String MINHAS_TAREFAS = "minhas-tarefas";
	private static final String MINHAS_PETICOES = "minhas-peticoes";
	private static final Map<String, Dashboard> mapeamentoPapelDashboard = new HashMap<>();

	static {
		mapeamentoPapelDashboard.put("peticionador", buildDashboardFromDashlets(MINHAS_TAREFAS, MINHAS_PETICOES));
		mapeamentoPapelDashboard.put("preautuador", buildDashboardFromDashlets(MINHAS_TAREFAS));
		mapeamentoPapelDashboard.put("autuador", buildDashboardFromDashlets(MINHAS_TAREFAS));
		mapeamentoPapelDashboard.put("distribuidor", buildDashboardFromDashlets(MINHAS_TAREFAS));
		mapeamentoPapelDashboard.put("recebedor", buildDashboardFromDashlets(MINHAS_TAREFAS, MINHAS_PETICOES));
		mapeamentoPapelDashboard.put("cartoraria", buildDashboardFromDashlets(MINHAS_TAREFAS));
		mapeamentoPapelDashboard.put("representante", buildDashboardFromDashlets(MINHAS_TAREFAS, MINHAS_PETICOES));
		mapeamentoPapelDashboard.put("gestor-autuacao", buildDashboardFromDashlets("grafico-gestao"));
	}

	private static Dashboard buildDashboardFromDashlets(String... dashletsNames) {
		List<Dashlet> dashlets = new ArrayList<>();
		Arrays.asList(dashletsNames).forEach(d -> dashlets.add(new Dashlet(d)));
		return new Dashboard(dashlets);
	}

	@Override
	public Dashboard consultarPadraoDoPapel(String papel) {
		Dashboard dashboard = mapeamentoPapelDashboard.get(papel);
		if (dashboard == null) { // Caso não tenha um dashboard designado para o papel, monta um padrão.
			dashboard = new Dashboard(Arrays.asList(new Dashlet("minhas-tarefas")));
		}
		return dashboard;
	}

}
