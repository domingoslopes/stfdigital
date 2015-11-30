package br.jus.stf.plataforma.acessos.infra.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.plataforma.acessos.domain.model.Permissao;
import br.jus.stf.plataforma.acessos.domain.model.Recurso;
import br.jus.stf.plataforma.acessos.domain.model.RecursoRepository;
import br.jus.stf.plataforma.acessos.domain.model.TipoRecurso;
import br.jus.stf.shared.RecursoId;

@Repository
public class RecursoRepositoryImpl extends SimpleJpaRepository<Recurso, Long> implements RecursoRepository {
	
	private EntityManager entityManager;
	
	@Autowired
	public RecursoRepositoryImpl(EntityManager entityManager) {
		super(Recurso.class, entityManager);
		this.entityManager = entityManager;
	}
	
	@Override
	public Recurso findOne(Long sequencial) {
		return super.findOne(sequencial);
	}
	
	@Override
	public Recurso findOne(String nome, TipoRecurso tipo) {
		TypedQuery<Recurso> query = entityManager.createQuery("SELECT recurso FROM Recurso recurso WHERE recurso.nome = :nome AND recurso.tipo = :tipo", Recurso.class);
		query.setParameter("nome", nome);
		query.setParameter("tipo", tipo);

		return query.getSingleResult();
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
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Permissao> findPermissaoByRecurso(RecursoId id) {
		Query query = entityManager.createQuery("SELECT perm FROM Recurso recu INNER JOIN recu.permissoesExigidas perm WITH recu.id = :id");
		query.setParameter("id", id);
		
		return query.getResultList();
	}

}