package br.jus.stf.plataforma.identidades.infra.persistence;

import javax.persistence.EntityManager;

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

	@Autowired
	public AssociadoRepositoryImpl(EntityManager entityManager) {
		super(Associado.class, entityManager);
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

}
