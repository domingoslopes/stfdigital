package br.jus.stf.plataforma.acessos.infra.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.plataforma.acessos.domain.model.Permissao;
import br.jus.stf.plataforma.acessos.domain.model.Recurso;
import br.jus.stf.plataforma.acessos.domain.model.Segmento;
import br.jus.stf.plataforma.acessos.domain.model.TipoInformacao;
import br.jus.stf.plataforma.acessos.domain.model.TipoSegmento;
import br.jus.stf.plataforma.acessos.domain.model.Usuario;
import br.jus.stf.plataforma.acessos.domain.model.UsuarioRepository;

@Repository
public class UsuarioRepositoryImpl extends SimpleJpaRepository<Usuario, Long> implements UsuarioRepository {
	
	private EntityManager entityManager;
	
	@Autowired
	public UsuarioRepositoryImpl(EntityManager entityManager) {
		super(Usuario.class, entityManager);
		this.entityManager = entityManager;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Usuario findOne(Long sequencial) {
		return super.findOne(sequencial);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Usuario save(Usuario principal) {
		return super.save(principal);
	}
	
	@Override
	public TipoInformacao findOneTipoInformacao(Long sequencial) {
		Query query = entityManager.createQuery("SELECT tipo FROM TipoInformacao tipo WHERE tipo.sequencial = :id");
		query.setParameter("id", sequencial);
		
		return (TipoInformacao)query.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TipoInformacao> findAllTipoInformacao() {
		Query query = entityManager.createQuery("SELECT tipo FROM TipoInformacao tipo ORDER BY tipo.nome");
		
		return query.getResultList();
	}
	
	@Override
	public TipoSegmento findOneTipoSegmento(Long sequencial) {
		Query query = entityManager.createQuery("SELECT tipo FROM TipoSegmento tipo WHERE tipo.sequencial = :id");
		query.setParameter("id", sequencial);
		
		return (TipoSegmento)query.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TipoSegmento> findAllTipoSegmento() {
		Query query = entityManager.createQuery("SELECT tipo FROM Tipo tipo ORDER BY tipo.nome");
		
		return query.getResultList();
	}
	
	@Override
	public Segmento findOneSegmento(Long sequencial) {
		Query query = entityManager.createQuery("SELECT segmento FROM Segmento segmento WHERE segmento.sequencial = :id");
		query.setParameter("id", sequencial);
		
		return (Segmento)query.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Segmento> findAllSegmento() {
		Query query = entityManager.createQuery("SELECT segmento FROM Segmento segmento ORDER BY segmento.nome");
		
		return query.getResultList();
	}
	
	@Override
	public Recurso findOneRecurso(Long sequencial) {
		Query query = entityManager.createQuery("SELECT recurso FROM Recurso recurso WHERE recurso.sequencial = :id");
		query.setParameter("id", sequencial);
		
		return (Recurso)query.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Recurso> findAllRecurso() {
		Query query = entityManager.createQuery("SELECT recurso FROM Recurso recurso ORDER BY recurso.nome");
		
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Permissao> findByPermissaoUsuario(String login) {
		Query query = entityManager.createQuery("SELECT perm FROM Usuario usua INNER JOIN usua.permissoes perm WITH usua.login = :login");
		query.setParameter("login", login);
		
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Permissao> findByPermissaoRecurso(Long sequencial) {
		Query query = entityManager.createQuery("SELECT perm FROM Recurso recu INNER JOIN recu.permissoesExigidas perm WITH recu.sequencial = :id");
		query.setParameter("id", sequencial);
		
		return query.getResultList();
	}

}
