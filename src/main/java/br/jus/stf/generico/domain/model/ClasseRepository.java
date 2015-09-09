package br.jus.stf.generico.domain.model;

import java.util.List;

import org.springframework.data.repository.Repository;

import br.jus.stf.shared.domain.model.ClasseId;

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

}