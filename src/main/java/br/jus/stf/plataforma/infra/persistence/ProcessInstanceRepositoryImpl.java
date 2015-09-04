package br.jus.stf.plataforma.infra.persistence;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.plataforma.domain.model.ProcessInstance;
import br.jus.stf.plataforma.domain.model.ProcessInstanceRepository;
import br.jus.stf.shared.domain.model.ProcessInstanceId;

/**
 * @author Lucas.Rodrigues
 *
 */
@Repository
public class ProcessInstanceRepositoryImpl extends SimpleJpaRepository<ProcessInstance, ProcessInstanceId> implements ProcessInstanceRepository {

	@Autowired
	public ProcessInstanceRepositoryImpl(EntityManager entityManager) {
		super(ProcessInstance.class, entityManager);
	}

	@Override
	public ProcessInstance find(ProcessInstanceId id) {
		return super.findOne(id);
	}

	@Override
	public void store(ProcessInstance processInstance) {
		super.save(processInstance);
	}

}
