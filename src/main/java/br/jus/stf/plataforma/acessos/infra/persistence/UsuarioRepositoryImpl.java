package br.jus.stf.plataforma.acessos.infra.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.plataforma.acessos.domain.model.Grupo;
import br.jus.stf.plataforma.acessos.domain.model.Papel;
import br.jus.stf.plataforma.acessos.domain.model.Permissao;
import br.jus.stf.plataforma.acessos.domain.model.Segmento;
import br.jus.stf.plataforma.acessos.domain.model.TipoInformacao;
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
	public Usuario findOne(String login) {
		return super.findOne(new Specification<Usuario>(){
			@Override
			public Predicate toPredicate(Root<Usuario> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("login"), login);
			}} );
	}

	@SuppressWarnings("unchecked")
	@Override
	public Usuario save(Usuario principal) {
		return super.save(principal);
	}
	
	@Override
	public Papel findOnePapel(Long sequencial) {
		return entityManager.find(Papel.class, sequencial);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Papel> findAllPapel() {
		Query query = entityManager.createQuery("SELECT papel FROM Papel papel ORDER BY papel.nome");
		
		return query.getResultList();
	}

	@Override
	public Grupo findOneGrupo(Long sequencial) {
		return entityManager.find(Grupo.class, sequencial);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Grupo> findAllGrupo() {
		Query query = entityManager.createQuery("SELECT grupo FROM Grupo grupo ORDER BY grupo.nome");
		
		return query.getResultList();
	}
	
	@Override
	public TipoInformacao findOneTipoInformacao(Long sequencial) {
		return entityManager.find(TipoInformacao.class, sequencial);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TipoInformacao> findAllTipoInformacao() {
		Query query = entityManager.createQuery("SELECT tipo FROM TipoInformacao tipo ORDER BY tipo.nome");
		
		return query.getResultList();
	}
	
	@Override
	public Segmento findOneSegmento(Long sequencial) {
		return entityManager.find(Segmento.class, sequencial);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Segmento> findAllSegmento() {
		Query query = entityManager.createQuery("SELECT segmento FROM Segmento segmento ORDER BY segmento.nome");
		
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Permissao> findByPermissaoPapel(Long sequencial) {
		Query query = entityManager.createQuery("SELECT perm FROM Papel pape INNER JOIN pape.permissoes perm WITH pape.sequencial = :id");
		query.setParameter("id", sequencial);
		
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Permissao> findByPermissaoGrupo(Long sequencial) {
		Query query = entityManager.createQuery("SELECT perm FROM Grupo grup INNER JOIN grup.permissoes perm WITH grup.sequencial = :id");
		query.setParameter("id", sequencial);
		
		return query.getResultList();
	}

}