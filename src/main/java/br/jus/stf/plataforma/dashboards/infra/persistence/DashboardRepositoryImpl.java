package br.jus.stf.plataforma.dashboards.infra.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.plataforma.dashboards.domain.model.Dashboard;
import br.jus.stf.plataforma.dashboards.domain.model.DashboardId;
import br.jus.stf.plataforma.dashboards.domain.model.DashboardRepository;
import br.jus.stf.plataforma.shared.security.annotation.SecuredResource;

/**
 * Implementação fake temporária do DashboardRepository. Essa implementação fixa
 * Dashboards padrões para os papéis.
 * 
 * @author Tomas.Godoi
 *
 */
@Repository
public class DashboardRepositoryImpl extends SimpleJpaRepository<Dashboard, DashboardId> implements DashboardRepository {
	
	private EntityManager entityManager;
	
	@Autowired
	public DashboardRepositoryImpl(EntityManager entityManager) {
		super(Dashboard.class, entityManager);
		this.entityManager = entityManager;
	}
	
	@Override
	@SecuredResource
	public List<Dashboard> listar() {
		return findAll();
	}

	@Override
	public DashboardId proximoId() {
		Query query = entityManager.createNativeQuery("SELECT PLATAFORMA.SEQ_DASHBOARD.NEXTVAL FROM DUAL", Long.class);
		Long sequencial = (Long) query.getSingleResult();
		return new DashboardId(sequencial);
	}

}
