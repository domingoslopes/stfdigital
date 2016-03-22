package br.jus.stf.processamentoinicial.suporte.infra.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.processamentoinicial.suporte.domain.model.Pais;
import br.jus.stf.processamentoinicial.suporte.domain.model.ProcedenciaGeograficaRepository;
import br.jus.stf.processamentoinicial.suporte.domain.model.UnidadeFederacao;

@Repository
public class ProcedenciaGeograficaRepositoryImpl extends SimpleJpaRepository<Pais, Long> implements ProcedenciaGeograficaRepository {
	
	private EntityManager entityManager;
	
	@Autowired
	public ProcedenciaGeograficaRepositoryImpl(EntityManager entityManager) {
		super(Pais.class, entityManager);
		this.entityManager = entityManager;
	}
	
	public Pais findOnePais(Long sequencial) {
		return super.findOne(sequencial);
	}
	
	public List<Pais> findAllPais() {
		return super.findAll();
	}
	
	public UnidadeFederacao findOneUnidadeFederacao(Long sequencial) {
		TypedQuery<UnidadeFederacao> query = entityManager.createQuery("SELECT uf FROM UnidadeFederacao uf WHERE uf.sequencial = :sequencial", UnidadeFederacao.class);
		query.setParameter("sequencial", sequencial);
		
		return query.getSingleResult();
	}
	
	public List<UnidadeFederacao> findAllUnidadeFederacao() {
		TypedQuery<UnidadeFederacao> query = entityManager.createQuery("SELECT uf FROM UnidadeFederacao uf ORDER BY uf.sigla", UnidadeFederacao.class);
		
		return query.getResultList();
	}
	
	public List<UnidadeFederacao> findUnidadeFederacaoByPais(Pais pais) {
		TypedQuery<UnidadeFederacao> query = entityManager.createQuery("SELECT uf FROM UnidadeFederacao uf WHERE uf.pais = :pais ORDER BY uf.sigla", UnidadeFederacao.class);
		query.setParameter("pais", pais);
		
		return query.getResultList();
	}

}