package br.jus.stf.plataforma.acessos.infra.persistence;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.plataforma.acessos.domain.model.Grupo;
import br.jus.stf.plataforma.acessos.domain.model.GrupoRepository;
import br.jus.stf.plataforma.acessos.domain.model.Recurso;
import br.jus.stf.plataforma.acessos.domain.model.TipoGrupo;
import br.jus.stf.shared.GrupoId;

@Repository
public class GrupoRepositoryImpl extends SimpleJpaRepository<Grupo, GrupoId> implements GrupoRepository {
	
	private EntityManager entityManager;
	
	@Autowired
	public GrupoRepositoryImpl(EntityManager entityManager) {
		super(Grupo.class, entityManager);
		this.entityManager = entityManager;
	}
	
	@Override
	public Grupo findOne(String nome, TipoGrupo tipo) {
		TypedQuery<Grupo> query = entityManager.createQuery("SELECT grupo FROM Grupo grupo WHERE grupo.nome = :nome AND grupo.tipo = :tipo", Grupo.class);
		query.setParameter("nome", nome);
		query.setParameter("tipo", tipo);
		
		return query.getSingleResult();
	}
	
	@Override
	public List<Recurso> findRecursoByGrupo(GrupoId id) {
		TypedQuery<Recurso> query = entityManager.createQuery("SELECT recu FROM Grupo grupo INNER JOIN grupo.recursos recu WITH grupo.id = :id", Recurso.class);
		query.setParameter("id", id);
		
		return query.getResultList();
	}
	
	@Override
	public GrupoId nextId() {
		Query query = entityManager.createNativeQuery("SELECT plataforma.seq_grupo.NEXTVAL FROM DUAL");
		Long sequencial = ((BigInteger) query.getSingleResult()).longValue();
		return new GrupoId(sequencial);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Grupo save(Grupo grupo) {
		return super.save(grupo);
	}

}