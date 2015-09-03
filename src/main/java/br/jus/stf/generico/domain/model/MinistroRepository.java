package br.jus.stf.generico.domain.model;

import java.util.List;

import br.jus.stf.shared.domain.model.MinistroId;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:34:02
 */
public interface MinistroRepository {

	/**
	 * 
	 * @param ministroId
	 */
	public Ministro find(MinistroId codigo);
	
	public List<Ministro> findAll();

}