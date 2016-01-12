package br.jus.stf.processamentoinicial.suporte.domain.model;

import java.util.List;

import org.springframework.data.repository.Repository;

import br.jus.stf.shared.ClasseId;
import br.jus.stf.shared.PreferenciaId;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:34:02
 */
public interface ClasseRepository extends Repository<Classe, ClasseId> {
	
	/**
	 * Pesquisa uma classe
	 * 
	 * @param classeId
	 * @return
	 */
	public Classe findOne(ClasseId classeId);
	
	/**
	 * Pesquisa todas as classes
	 * 
	 * @return lista de classes
	 */
	public List<Classe> findAll();
	
	/**
	 * @param tipo
	 * @return
	 */
	public List<Classe> findClasseByTipo(TipoProcesso tipo);
	
	/**
	 * @param id
	 * @return
	 */
	public Preferencia findOnePreferencia(PreferenciaId id);
	
	/**
	 * Recupera a lista de preferências
	 * 
	 * @return a lista de preferências
	 */
	public List<Preferencia> findAllPreferencia();

}