package br.jus.stf.processamentoinicial.suporte.infra.persistence;

import java.math.BigInteger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.processamentoinicial.suporte.domain.model.Texto;
import br.jus.stf.processamentoinicial.suporte.domain.model.TextoRepository;
import br.jus.stf.shared.TextoId;

/**
 * Repositório de textos.
 * 
 * @author Tomas.Godoi
 *
 */
@Repository
public class TextoRepositoryImpl extends SimpleJpaRepository<Texto, TextoId> implements TextoRepository {

	private EntityManager entityManager;
	
	@Autowired
	public TextoRepositoryImpl(EntityManager entityManager) {
		super(Texto.class, entityManager);
		this.entityManager = entityManager;
	}


	@Override
	public TextoId nextId() {
		Query query = entityManager.createNativeQuery("SELECT corporativo.seq_texto.NEXTVAL FROM DUAL");
		Long sequencial = ((BigInteger) query.getSingleResult()).longValue();
		return new TextoId(sequencial);
	}

}
