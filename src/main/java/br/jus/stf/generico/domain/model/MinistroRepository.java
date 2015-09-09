package br.jus.stf.generico.domain.model;

import java.util.List;

import org.springframework.data.repository.Repository;

import br.jus.stf.shared.domain.model.MinistroId;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:34:02
 */
public interface MinistroRepository extends Repository<Ministro, MinistroId> {

	/**
	 * Pesquisa um ministro
	 * 
	 * @param codigo
	 * @return o ministro
	 */
	public Ministro findOne(MinistroId ministroId);
	
	/**
	 * Pesquisa todos os ministros
	 * 
	 * @return
	 */
	public List<Ministro> findAll();

}