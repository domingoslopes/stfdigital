package br.jus.stf.autuacao.infra.persistence;

import java.math.BigInteger;

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

	private EntityManager entityManager;
	
	@Autowired
	public ProcessoRepositoryImpl(EntityManager entityManager) {
		super(Processo.class, entityManager);
		this.entityManager = entityManager;
	}

	@Override
	public ProcessoId nextId() {
		Query query = entityManager.createNativeQuery("SELECT autuacao.seq_processo.NEXTVAL FROM DUAL");
		Long sequencial = ((BigInteger) query.getSingleResult()).longValue();
		return new ProcessoId(sequencial);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Processo save(Processo processo) {
		return super.save(processo);
	}
	
	@Override
	public Long nextNumero(ClasseId classe) {
		synchronized (this) {
			String key = classe.toString();
			Query query = entityManager.createNativeQuery("SELECT NVL(MAX(num_processo), 0) FROM autuacao.processo WHERE sig_classe = :classe");
			Long ultimoNumero = ((BigInteger) query.setParameter("classe", key).getSingleResult()).longValue();
			Long proximoNumero = ultimoNumero + 1;
			
			return proximoNumero;	
		}
	}
	
}
