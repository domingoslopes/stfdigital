package br.jus.stf.autuacao.infra.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.autuacao.domain.model.Processo;
import br.jus.stf.autuacao.domain.model.ProcessoRepository;
import br.jus.stf.shared.domain.model.ClasseId;
import br.jus.stf.shared.domain.model.ProcessoId;

/**
 * @author Lucas.Rodrigues
 *
 */
@Repository
public class ProcessoRepositoryImpl extends SimpleJpaRepository<Processo, Long> implements ProcessoRepository {

	private EntityManager entityManager;
	private Map<String, Long> numero = new HashMap<String, Long>();
	
	@Autowired
	public ProcessoRepositoryImpl(EntityManager entityManager) {
		super(Processo.class, entityManager);
		this.entityManager = entityManager;
	}
	
	@Override
	public Processo find(ProcessoId id) {
		return super.findOne(id.toLong());
	}
	
	@Override
	public void store(Processo processo) {
		super.save(processo);
	}
	
	@Override
	public synchronized Long proximoNumero(ClasseId classe) {
		/*
		String query = "SELECT COALESCE(MAX(p.numero), 0) + 1 FROM Processo p WHERE p.classe = ?1";
		TypedQuery<Long> tquery = entityManager.createQuery(query, Long.class);
	    tquery.setParameter(1, classe);
		return tquery.getSingleResult();
		*/
		String key = classe.toString();
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
	public List<Processo> find(Specification<Processo> specification) {
		return super.findAll(specification);
	}

}
