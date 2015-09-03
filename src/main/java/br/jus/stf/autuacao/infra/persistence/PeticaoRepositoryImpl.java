package br.jus.stf.autuacao.infra.persistence;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.autuacao.domain.model.Peticao;
import br.jus.stf.autuacao.domain.model.PeticaoRepository;
import br.jus.stf.autuacao.domain.model.PeticaoStatus;
import br.jus.stf.shared.domain.model.PeticaoId;

/**
 * @author Lucas.Rodrigues
 *
 */
@Repository
public class PeticaoRepositoryImpl extends SimpleJpaRepository<Peticao, Long> implements PeticaoRepository {

	private EntityManager entityManager;
	private Map<Integer, Long> numero = new HashMap<Integer, Long>();
	
	@Autowired
	public PeticaoRepositoryImpl(EntityManager entityManager) {
		super(Peticao.class, entityManager);
		this.entityManager = entityManager;
	}
	
	@Override
	public Peticao find(PeticaoId id) {
		return super.findOne(id.toLong());
	}
	
	@Override
	public void store(Peticao peticao) {
		super.save(peticao);
	}
	
	@Override
	public synchronized Long proximoNumero() {
		/*
		String query = "SELECT COALESCE(MAX(p.numero), 0) + 1 FROM Peticao p WHERE p.ano = ?1";
		TypedQuery<Long> tquery = entityManager.createQuery(query, Long.class);
		tquery.setParameter(1, Calendar.getInstance().get(Calendar.YEAR));
		return tquery.getSingleResult();
		*/
		Integer key = Calendar.getInstance().get(Calendar.YEAR);
		if (numero.containsKey(key)) {
			Long num = numero.get(key) + 1;
			numero.put(key, num);
			return num;
		} else {
			numero.put(key, 1L);
			return 1L;
		}
	}

	@Override
	public List<Peticao> find(Specification<Peticao> specification) {
		return super.findAll(specification);
	}

	@Override
	public List<Peticao> findStatus(PeticaoStatus status) {
		Specification<Peticao> spec = new Specification<Peticao>() {

			@Override
			public Predicate toPredicate(Root<Peticao> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("status"), status);
			}
		};
		return super.findAll(spec);
	}

}
