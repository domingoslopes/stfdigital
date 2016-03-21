package br.jus.stf.processamentoinicial.suporte.infra.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.processamentoinicial.suporte.domain.model.TribunalJuizo;
import br.jus.stf.processamentoinicial.suporte.domain.model.TribunalJuizoRepository;
import br.jus.stf.processamentoinicial.suporte.domain.model.UnidadeFederacao;

@Repository
public class TribunalJuizoRepositoryImpl extends SimpleJpaRepository<TribunalJuizo, Long> implements TribunalJuizoRepository {
	
	private EntityManager entityManager;
	
	@Autowired
	public TribunalJuizoRepositoryImpl(EntityManager entityManager) {
		super(TribunalJuizo.class, entityManager);
		this.entityManager = entityManager;
	}
	
	public TribunalJuizo findOne(Long sequencial) {
		return super.findOne(sequencial);
	}
	
	public List<TribunalJuizo> findAll() {
		return super.findAll();
	}
	
	public List<TribunalJuizo> findByUnidadeFederacao(UnidadeFederacao uf) {
		TypedQuery<TribunalJuizo> query = entityManager.createQuery("SELECT tj FROM TribunalJuizo tj INNER JOIN tj.ufsAtuacao uf WITH uf = :uf", TribunalJuizo.class);
		query.setParameter("uf", uf);
		
		return query.getResultList();
	}

}