package br.jus.stf.jurisprudencia.controletese.infra.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.jurisprudencia.controletese.domain.model.Tese;
import br.jus.stf.jurisprudencia.controletese.domain.model.TeseRepository;
import br.jus.stf.jurisprudencia.controletese.domain.model.TipoTese;
import br.jus.stf.shared.TeseId;

/**
 * Implementação do repositório de teses.
 * 
 * @author Anderson.Araujo
 * 
 * @since 28.01.2016
 *
 */
@Repository
public class TeseRepositoryImpl  extends SimpleJpaRepository<Tese, TeseId> implements TeseRepository {
	private EntityManager entityManager;
	
	@Autowired
	public TeseRepositoryImpl(EntityManager entityManager) {
		super(Tese.class, entityManager);
		this.entityManager = entityManager;
	}
	
	/**
	 * Recupera a tese de acordo com o código informado.
	 * 
	 * @param TeseId Id da tese.
	 * 
	 * @return Dados da tese.
	 */
	public Tese findOne(TeseId teseId) {
		return super.findOne(teseId);
	}
	
	/**
	 * Recupera todas as teses cadastradas.
	 * 
	 * @return Lista de teses.
	 */
	public List<Tese> findAll() {
		return super.findAll();
	}
	
	/**
	 * Recupera as teses de acordo com os parâmetros informados.
	 * 
	 * @param tipo Tipo de tese.
	 * @param numero Nº da tese.
	 * 
	 * @return Lista de teses.
	 */
	public List<Tese> findTeseByTipo(TipoTese tipo, Long numero){
		TypedQuery<Tese> query = entityManager.createQuery("SELECT tese FROM Tese tese WHERE tese.tipo = :tipo AND tese.numero = :numero", Tese.class);
		query.setParameter("tipo", tipo);
		query.setParameter("numero", numero);
		
		return query.getResultList();
	}

	/**
	 * Recupera as teses com os ids
	 * 
	 * @param tesesIds
	 * @return A lista de teses
	 */
	@Override
	public List<Tese> findTesesByIds(List<TeseId> tesesIds) {
		TypedQuery<Tese> query = entityManager.createQuery("FROM Tese tese WHERE tese.codigo in :ids", Tese.class);
		query.setParameter("ids", tesesIds);
		return query.getResultList();
	}
}
