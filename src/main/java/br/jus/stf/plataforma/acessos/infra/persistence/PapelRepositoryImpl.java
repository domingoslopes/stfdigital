package br.jus.stf.plataforma.acessos.infra.persistence;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.plataforma.acessos.domain.model.Papel;
import br.jus.stf.plataforma.acessos.domain.model.PapelRepository;
import br.jus.stf.plataforma.acessos.domain.model.Permissao;
import br.jus.stf.shared.PapelId;
import br.jus.stf.shared.RecursoId;

@Repository
public class PapelRepositoryImpl extends SimpleJpaRepository<Papel, PapelId> implements PapelRepository {
	
	private EntityManager entityManager;
	
	@Autowired
	public PapelRepositoryImpl(EntityManager entityManager) {
		super(Papel.class, entityManager);
		this.entityManager = entityManager;
	}
	
	@Override
	public Papel findOne(String nome) {
		TypedQuery<Papel> query = entityManager.createQuery("SELECT papel FROM Papel papel WHERE papel.nome = :nome", Papel.class);
		query.setParameter("nome", nome);
		
		return query.getSingleResult();
	}
			
	@SuppressWarnings("unchecked")
	@Override
	public List<Permissao> findPermissaoByPapel(PapelId id) {
		Query query = entityManager.createQuery("SELECT perm FROM Papel papel INNER JOIN papel.permissoes perm WITH papel.id = :id");
		query.setParameter("id", id);
		
		return query.getResultList();
	}
	
	@Override
	public PapelId nextId() {
		Query query = entityManager.createNativeQuery("SELECT plataforma.seq_papel.NEXTVAL FROM DUAL");
		Long sequencial = ((BigInteger) query.getSingleResult()).longValue();
		return new PapelId(sequencial);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Papel save(Papel papel) {
		return super.save(papel);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Papel> findPapelByRecurso(RecursoId id) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT papel FROM Papel papel INNER JOIN papel.permissoes permiss WHERE permiss.id IN (");
		sql.append("SELECT perm.id FROM Recurso recu INNER JOIN recu.permissoesExigidas perm WITH recu.id = :id))");
		Query query = entityManager.createQuery(sql.toString());
		query.setParameter("id", id);
		
		return query.getResultList();
	}

}