package br.jus.stf.processamentoinicial.suporte.domain.model;

import java.util.List;

import br.jus.stf.shared.ClasseId;
import br.jus.stf.shared.PreferenciaId;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:34:02
 */
public interface ClasseRepository {
	
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
	public Preferencia findOnePreferencia(PreferenciaId preferenciaId);
	
	/**
	 * Recupera a lista de preferências
	 * 
	 * @return a lista de preferências
	 */
	public List<Preferencia> findAllPreferencia();
	
	/**
	 * @param classeId
	 * @return
	 */
	public List<Preferencia> findPreferenciaByClasse(ClasseId classeId);

}