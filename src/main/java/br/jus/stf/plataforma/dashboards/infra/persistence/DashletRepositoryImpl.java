package br.jus.stf.plataforma.dashboards.infra.persistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.jus.stf.plataforma.dashboards.domain.model.Dashlet;
import br.jus.stf.plataforma.dashboards.domain.model.DashletRepository;
import br.jus.stf.plataforma.shared.security.stereotype.SecuredResource;

/**
 * Implementação fake temporária do DashboardRepository. Essa implementação fixa
 * Dashboards padrões para os papéis.
 * 
 * @author Tomas.Godoi
 *
 */
@Repository
public class DashletRepositoryImpl implements DashletRepository {
	
	private static final String MINHAS_PETICOES = "minhas-peticoes";
	private static final String MINHAS_TAREFAS = "minhas-tarefas";
	private static final String GRAFICO_GESTAO = "grafico-gestao";
	
	//TODO Pesquisar dashlets por dashboards
	@SecuredResource
	public List<Dashlet> consultarPadrao() {
		return new ArrayList<Dashlet>(Arrays.asList(criarDashlet(MINHAS_PETICOES), 
				criarDashlet(MINHAS_TAREFAS),
				criarDashlet(GRAFICO_GESTAO)));
	}
	
	private Dashlet criarDashlet(String nome) {
		Dashlet dashlet = new Dashlet(nome);
		return dashlet;
	}

}
