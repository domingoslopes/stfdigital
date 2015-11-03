package br.jus.stf.processamentoinicial.autuacao.infra.persistence;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.processamentoinicial.autuacao.domain.model.Orgao;
import br.jus.stf.processamentoinicial.autuacao.domain.model.Peticao;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoRepository;
import br.jus.stf.processamentoinicial.autuacao.domain.model.TipoPeca;
import br.jus.stf.shared.PeticaoId;

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
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Peticao> T findOne(PeticaoId id) {
		return (T) super.findOne(id);
	}

	@Override
	public PeticaoId nextId() {
		Query query = entityManager.createNativeQuery("SELECT autuacao.seq_peticao.NEXTVAL FROM DUAL");
		Long sequencial = ((BigInteger) query.getSingleResult()).longValue();
		return new PeticaoId(sequencial);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Peticao> T save(Peticao peticao) {
		return (T) super.save(peticao);
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
	
	@Override
	public TipoPeca findOneTipoPeca(Long id) {
		Query query = entityManager.createQuery("SELECT tipo FROM TipoPeca tipo WHERE tipo.sequencial = :id");
		query.setParameter("id", id);
		
		return (TipoPeca)query.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TipoPeca> findAllTipoPeca() {
		Query query = entityManager.createQuery("SELECT tipo FROM TipoPeca tipo ORDER BY tipo.nome");
		
		return query.getResultList();
	}
	
	@Override
	public Orgao findOneOrgao(Long id) {
		Query query = entityManager.createQuery("SELECT orgao FROM Orgao orgao WHERE orgao.sequencial = :id");
		query.setParameter("id", id);
		
		return (Orgao)query.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Orgao> findAllOrgao() {
		Query query = entityManager.createQuery("SELECT orgao FROM Orgao orgao ORDER BY orgao.nome");
		
		return query.getResultList();
	}
	
}
