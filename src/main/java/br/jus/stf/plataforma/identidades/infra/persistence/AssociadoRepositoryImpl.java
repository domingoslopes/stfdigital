package br.jus.stf.plataforma.identidades.infra.persistence;

import java.math.BigInteger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.plataforma.identidades.domain.model.Associado;
import br.jus.stf.plataforma.identidades.domain.model.AssociadoRepository;

/**
 * @author Rafael.Alencar
 *
 */
@Repository
public class AssociadoRepositoryImpl extends SimpleJpaRepository<Associado, Long> implements AssociadoRepository {
	
	private EntityManager entityManager;

	@Autowired
	public AssociadoRepositoryImpl(EntityManager entityManager) {
		super(Associado.class, entityManager);
		this.entityManager = entityManager;
	}
	
	@Override
	public Associado findOne(Long sequencial) {
		return super.findOne(sequencial);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Associado save(Associado associado) {
		return super.save(associado); 
	}
	
	@Override
	public Long nextId() {
		Query query = entityManager.createNativeQuery("SELECT CORPORATIVO.SEQ_ASSOCIADO.NEXTVAL FROM DUAL");
		return ((BigInteger) query.getSingleResult()).longValue();
	}

}
