package br.jus.stf.jurisprudencia.controletese.infra.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.jurisprudencia.controletese.domain.model.Assunto;
import br.jus.stf.jurisprudencia.controletese.domain.model.AssuntoRepository;
import br.jus.stf.shared.AssuntoId;

/**
 * Recupera os dados sobre assuntos.
 * 
 * @author Anderson.Araujo
 * 
 * @since 26.01.2016
 *
 */
@Repository
public class AssuntoRepositoryImpl extends SimpleJpaRepository<Assunto, AssuntoId> implements AssuntoRepository {
	
	private EntityManager entityManager;
	
	@Autowired
	public AssuntoRepositoryImpl(EntityManager entityManager) {
		super(Assunto.class, entityManager);
		this.entityManager = entityManager;
	}
	
	public Assunto findOne(AssuntoId assuntoId) {
		return super.findOne(assuntoId);
	}
	
	public List<Assunto> findAll() {
		return super.findAll();
	}
	
	public List<Assunto> findAssuntoByDescricao(String descricao) {
		TypedQuery<Assunto> query = entityManager.createQuery("SELECT assunto FROM Assunto assunto WHERE assunto.descricao LIKE :descricao", Assunto.class);
		query.setParameter("descricao", "%" + descricao + "%");
		
		return query.getResultList();
	}
}
