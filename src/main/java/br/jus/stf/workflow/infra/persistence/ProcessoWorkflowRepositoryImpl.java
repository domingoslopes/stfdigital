package br.jus.stf.workflow.infra.persistence;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.shared.domain.model.ProcessoWorkflow;
import br.jus.stf.shared.domain.model.ProcessoWorkflowId;
import br.jus.stf.workflow.domain.model.ProcessoWorkflowRepository;

/**
 * @author Lucas.Rodrigues
 *
 */
@Repository
public class ProcessoWorkflowRepositoryImpl extends SimpleJpaRepository<ProcessoWorkflow, ProcessoWorkflowId> implements ProcessoWorkflowRepository {

	private EntityManager entityManager;

	@Autowired
	public ProcessoWorkflowRepositoryImpl(EntityManager entityManager) {
		super(ProcessoWorkflow.class, entityManager);
		this.entityManager = entityManager;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ProcessoWorkflow save(ProcessoWorkflow processoWorkflow) {
		return super.save(processoWorkflow);
	}

	@Override
	public void updateStatus(ProcessoWorkflowId processoWorkflowId, String status) {
		Query query = entityManager.createQuery("UPDATE ProcessoWorkflow pw SET pw.status = :status WHERE pw.id = :id");
		query.setParameter("status", status);
		query.setParameter("id", processoWorkflowId);
		query.executeUpdate();
	}

}
