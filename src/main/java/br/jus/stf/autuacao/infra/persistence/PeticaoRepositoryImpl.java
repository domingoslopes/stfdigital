package br.jus.stf.autuacao.infra.persistence;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

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

	private Map<Integer, Long> numerosPeticao = new HashMap<Integer, Long>();
	
	private EntityManager entityManager;
	
	@Autowired
	public PeticaoRepositoryImpl(EntityManager entityManager) {
		super(Peticao.class, entityManager);
		this.entityManager = entityManager;
	}

	@Override
	public PeticaoId nextId() {
		Query query = entityManager.createNativeQuery("SELECT AUTUACAO.SEQ_PETICAO.NEXTVAL FROM DUAL");
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
			if (numerosPeticao.containsKey(key)) {
				Long num = numerosPeticao.get(key) + 1;
				numerosPeticao.put(key, num);
				return num;
			} else {
				numerosPeticao.put(key, 1L);
				return 1L;
			}
		}
	}
	
}
