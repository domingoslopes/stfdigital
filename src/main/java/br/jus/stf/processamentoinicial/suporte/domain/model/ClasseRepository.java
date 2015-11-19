package br.jus.stf.processamentoinicial.suporte.domain.model;

import java.util.List;

import org.springframework.data.repository.Repository;

import br.jus.stf.shared.ClasseId;

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