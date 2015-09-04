package br.jus.stf.generico.infra.persistence;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.generico.domain.model.Ministro;
import br.jus.stf.generico.domain.model.MinistroRepository;
import br.jus.stf.shared.domain.model.MinistroId;

/**
 * @author Lucas.Rodrigues
 *
 */
@Repository
public class MinistroRepositoryImpl extends SimpleJpaRepository<Ministro, Long> implements MinistroRepository {

	@Autowired
	public MinistroRepositoryImpl(EntityManager entityManager) {
		super(Ministro.class, entityManager);
	}

	@Override
	public Ministro find(MinistroId codigo) {
		return super.findOne(codigo.toLong());
	}

}
