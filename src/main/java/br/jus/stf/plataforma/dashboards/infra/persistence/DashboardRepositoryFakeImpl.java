package br.jus.stf.plataforma.dashboards.infra.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;

import br.jus.stf.plataforma.dashboards.domain.model.Dashboard;
import br.jus.stf.plataforma.dashboards.domain.model.DashboardRepository;
import br.jus.stf.plataforma.dashboards.domain.model.Dashlet;
import br.jus.stf.plataforma.shared.security.AcessosRestAdapter;
import br.jus.stf.plataforma.shared.security.SecurityContextUtil;

/**
 * Implementação fake temporária do DashboardRepository. Essa implementação fixa
 * Dashboards padrões para os papéis.
 * 
 * @author Tomas.Godoi
 *
 */
@Repository
public class DashboardRepositoryFakeImpl implements DashboardRepository, InitializingBean {
	
	private static final String MINHAS_PETICOES = "MINHAS-PETICOES";
	private static final String MINHAS_TAREFAS = "MINHAS-TAREFAS";
	private static final String GRAFICO_GESTAO = "GRAFICO-GESTAO";
	private static final String DASHLET = "DASHLET";
	
	@Autowired
	private AcessosRestAdapter acessosRestAdapter;
	
	private Map<String, Set<GrantedAuthority>> registry = new HashMap<String, Set<GrantedAuthority>>(3);

	@Override
	public Dashboard consultarPadrao() {
		List<Dashlet> dashlets = registry.entrySet().stream()
				.filter(entry -> SecurityContextUtil.userContainsAll(entry.getValue()))
				.map(entry -> new Dashlet(entry.getKey().toLowerCase()))
				.collect(Collectors.toList());
		return new Dashboard(dashlets);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		registry.put(MINHAS_PETICOES, acessosRestAdapter.carregarPermissoesRecurso(MINHAS_PETICOES, DASHLET));
		registry.put(MINHAS_TAREFAS, acessosRestAdapter.carregarPermissoesRecurso(MINHAS_TAREFAS, DASHLET));
		registry.put(GRAFICO_GESTAO, acessosRestAdapter.carregarPermissoesRecurso(GRAFICO_GESTAO, DASHLET));
	}

}
