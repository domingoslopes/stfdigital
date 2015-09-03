package br.jus.stf.generico.infra.persistence;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.generico.domain.model.Classe;
import br.jus.stf.generico.domain.model.ClasseRepository;
import br.jus.stf.shared.domain.model.ClasseId;

/**
 * @author Lucas.Rodrigues
 *
 */
@Repository
public class ClasseRepositoryImpl extends SimpleJpaRepository<Classe, ClasseId> implements ClasseRepository {

	@Autowired
	public ClasseRepositoryImpl(EntityManager entityManager) {
		super(Classe.class, entityManager);
	}

}
