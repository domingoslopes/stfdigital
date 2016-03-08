package br.jus.stf.processamentoinicial.suporte.infra.persistence;

import java.math.BigInteger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.processamentoinicial.suporte.domain.model.Modelo;
import br.jus.stf.processamentoinicial.suporte.domain.model.ModeloRepository;
import br.jus.stf.shared.ModeloId;

/**
 * Implementação do repositório de Modelo.
 * 
 * @author Tomas.Godoi
 *
 */
@Repository
public class ModeloRepositoryImpl extends SimpleJpaRepository<Modelo, ModeloId> implements ModeloRepository {

	private EntityManager entityManager;

	@Autowired
	public ModeloRepositoryImpl(EntityManager entityManager) {
		super(Modelo.class, entityManager);
		this.entityManager = entityManager;
	}

	@Override
	public ModeloId nextId() {
		Query query = entityManager.createNativeQuery("SELECT corporativo.seq_modelo.NEXTVAL FROM DUAL");
		Long sequencial = ((BigInteger) query.getSingleResult()).longValue();
		return new ModeloId(sequencial);
	}

}
