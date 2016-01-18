package br.jus.stf.processamentoinicial.suporte.infra.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.processamentoinicial.suporte.domain.model.Classe;
import br.jus.stf.processamentoinicial.suporte.domain.model.ClasseRepository;
import br.jus.stf.processamentoinicial.suporte.domain.model.Preferencia;
import br.jus.stf.processamentoinicial.suporte.domain.model.TipoProcesso;
import br.jus.stf.shared.ClasseId;
import br.jus.stf.shared.PreferenciaId;

@Repository
public class ClasseRepositoryImpl extends SimpleJpaRepository<Classe, ClasseId> implements ClasseRepository {
	
	private EntityManager entityManager;
	
	@Autowired
	public ClasseRepositoryImpl(EntityManager entityManager) {
		super(Classe.class, entityManager);
		this.entityManager = entityManager;
	}
	
	public Classe findOne(ClasseId classeId) {
		return super.findOne(classeId);
	}
	
	public List<Classe> findAll() {
		return super.findAll();
	}
	
	public List<Classe> findClasseByTipo(TipoProcesso tipo) {
		TypedQuery<Classe> query = entityManager.createQuery("SELECT classe FROM Classe classe WHERE classe.tipo = :tipo", Classe.class);
		query.setParameter("tipo", tipo);
		
		return query.getResultList();
	}
	
	public Preferencia findOnePreferencia(PreferenciaId preferenciaId) {
		TypedQuery<Preferencia> query = entityManager.createQuery("SELECT preferencia FROM Preferencia preferencia WHERE preferencia.codigo = :codigo", Preferencia.class);
		query.setParameter("codigo", preferenciaId);
		
		return query.getSingleResult();
	}
	
	public List<Preferencia> findAllPreferencia() {
		TypedQuery<Preferencia> query = entityManager.createQuery("SELECT preferencia FROM Preferencia preferencia", Preferencia.class);
		
		return query.getResultList();
	}
	
	public List<Preferencia> findPreferenciaByClasse(ClasseId classeId) {
		TypedQuery<Preferencia> query = entityManager.createQuery("SELECT pref FROM Classe clas INNER JOIN clas.preferencias pref WITH clas.sigla = :sigla", Preferencia.class);
		query.setParameter("sigla", classeId);
		
		return query.getResultList();
	}

}