package br.jus.stf.autuacao.infra.persistence;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
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
public class ProcessoRepositoryImpl extends SimpleJpaRepository<Processo, ProcessoId> implements ProcessoRepository {

	private static Map<String, Long> numerosProcesso = new HashMap<String, Long>();
	
	private EntityManager entityManager;
	
	@Autowired
	public ProcessoRepositoryImpl(EntityManager entityManager) {
		super(Processo.class, entityManager);
		this.entityManager = entityManager;
	}

	@Override
	public ProcessoId nextId() {
		Query query = entityManager.createNativeQuery("SELECT AUTUACAO.SEQ_PROCESSO.NEXTVAL FROM DUAL");
		Long sequencial = ((BigInteger) query.getSingleResult()).longValue();
		return new ProcessoId(sequencial);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Processo save(Processo processo) {
		return super.save(processo);
	}
	
	@Override
	public Long proximoNumero(ClasseId classe) {
		synchronized (classe) {
			String key = classe.toString();
			if (numerosProcesso.containsKey(key)) {
				Long num = numerosProcesso.get(key) + 1;
				numerosProcesso.put(key, num);
				return num;
			} else {
				numerosProcesso.put(key, 1L);
				return 1L;
			}	
		}
	}
	
}
