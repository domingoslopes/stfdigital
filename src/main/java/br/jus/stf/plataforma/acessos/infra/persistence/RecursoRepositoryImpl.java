package br.jus.stf.plataforma.acessos.infra.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.plataforma.acessos.domain.model.Recurso;
import br.jus.stf.plataforma.acessos.domain.model.RecursoRepository;
import br.jus.stf.plataforma.shared.security.resource.ResourceType;

@Repository
public class RecursoRepositoryImpl extends SimpleJpaRepository<Recurso, Long> implements RecursoRepository {
	
	private EntityManager entityManager;
	
	@Autowired
	public RecursoRepositoryImpl(EntityManager entityManager) {
		super(Recurso.class, entityManager);
		this.entityManager = entityManager;
	}
	
	@Override
	public Recurso findOne(String nome, ResourceType tipo) {
		TypedQuery<Recurso> query = entityManager.createQuery("SELECT recurso FROM Recurso recurso WHERE lower(recurso.nome) = lower(:nome) AND recurso.tipo = :tipo", Recurso.class);
		query.setParameter("nome", nome);
		query.setParameter("tipo", tipo);

		List<Recurso> results = query.getResultList();
		return results.isEmpty() ? null : results.get(0);
	}
	
	@Override
	public List<Recurso> findAllRecurso() {
		TypedQuery<Recurso> query = entityManager.createQuery("SELECT recurso FROM Recurso recurso ORDER BY recurso.nome", Recurso.class);
		
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Recurso save(Recurso recurso) {
		return super.save(recurso);
	}

}
