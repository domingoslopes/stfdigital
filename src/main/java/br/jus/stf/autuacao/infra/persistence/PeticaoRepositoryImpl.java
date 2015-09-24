package br.jus.stf.autuacao.infra.persistence;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.autuacao.domain.model.Peticao;
import br.jus.stf.autuacao.domain.model.PeticaoRepository;
import br.jus.stf.shared.domain.model.PeticaoId;

/**
 * @author Lucas.Rodrigues
 *
 */
@Repository
public class PeticaoRepositoryImpl extends SimpleJpaRepository<Peticao, PeticaoId> implements PeticaoRepository {

	private EntityManager entityManager;
	
	@Autowired
	public PeticaoRepositoryImpl(EntityManager entityManager) {
		super(Peticao.class, entityManager);
		this.entityManager = entityManager;
	}
	
	@Override
	public <T> T findOne(PeticaoId id, Class<T> clazz) {
		return Optional.ofNullable(super.findOne(id))
				.map(peticao -> clazz.cast(peticao))
				.orElse(null);
	}

	@Override
	public PeticaoId nextId() {
		Query query = entityManager.createNativeQuery("SELECT autuacao.seq_peticao.NEXTVAL FROM DUAL");
		Long sequencial = ((BigInteger) query.getSingleResult()).longValue();
		return new PeticaoId(sequencial);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Peticao save(Peticao peticao) {
		return super.save(peticao);
	}
	
	@Override
	public Long nextNumero() {
		synchronized (this) {
			Integer key = Calendar.getInstance().get(Calendar.YEAR);
			Query query = entityManager.createNativeQuery("SELECT NVL(MAX(num_peticao), 0) FROM autuacao.peticao WHERE num_ano_peticao = :anoPeticao");
			Long ultimoNumero = ((BigInteger) query.setParameter("anoPeticao", key).getSingleResult()).longValue();
			Long proximoNumero = ultimoNumero + 1;
			
			return proximoNumero;
		}
	}
	
}
