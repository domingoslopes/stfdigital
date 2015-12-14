package br.jus.stf.plataforma.acessos.infra.persistence;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.plataforma.acessos.domain.model.Segmento;
import br.jus.stf.plataforma.acessos.domain.model.SegmentoRepository;
import br.jus.stf.plataforma.acessos.domain.model.TipoInformacao;
import br.jus.stf.shared.SegmentoId;
import br.jus.stf.shared.TipoInformacaoId;

@Repository
public class SegmentoRepositoryImpl extends SimpleJpaRepository<Segmento, SegmentoId> implements SegmentoRepository {
	
	private EntityManager entityManager;
	
	@Autowired
	public SegmentoRepositoryImpl(EntityManager entityManager) {
		super(Segmento.class, entityManager);
		this.entityManager = entityManager;
	}
	
	@Override
	public Segmento findOne(String nome, TipoInformacaoId tipo) {
		TypedQuery<Segmento> query = entityManager.createQuery("SELECT segmento FROM Segmento segmento WHERE segmento.nome = :nome AND segmento.tipo = :tipo", Segmento.class);
		query.setParameter("nome", nome);
		query.setParameter("tipo", tipo);
		
		return query.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Segmento> findByTipoInformacao(TipoInformacaoId tipo) {
		Query query = entityManager.createQuery("SELECT segmento FROM Segmento segmento WHERE segmento.tipo = :tipo ORDER BY segmento.nome");
		query.setParameter("tipo", tipo);
		
		return query.getResultList();
	}
	
	@Override
	public SegmentoId nextId() {
		Query query = entityManager.createNativeQuery("SELECT plataforma.seq_segmento.NEXTVAL FROM DUAL");
		Long sequencial = ((BigInteger) query.getSingleResult()).longValue();
		return new SegmentoId(sequencial);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Segmento save(Segmento segmento) {
		return super.save(segmento);
	}
	
	@Override
	public TipoInformacao findOneTipoInformacao(TipoInformacaoId tipo) {
		TypedQuery<TipoInformacao> query = entityManager.createQuery("SELECT tipo FROM TipoInformacao tipo WHERE tipo.id = :id", TipoInformacao.class);
		query.setParameter("id", tipo);
		
		return query.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TipoInformacao> findAllTipoInformacao() {
		Query query = entityManager.createQuery("SELECT tipo FROM TipoInformacao tipo ORDER BY tipo.nome");
		
		return query.getResultList();
	}

}