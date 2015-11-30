package br.jus.stf.plataforma.identidades.domain.model;

import java.util.List;


/**
 * @author Rafael.Alencar
 * @created 30-ago-2015 18:34:02
 */
public interface AssociadoRepository {

	/**
	 * @param sequencial
	 * @return
	 */
	public Associado findOne(Long sequencial);
	
	/**
	 * @return
	 */
	public List<Associado> findAll();

	/**
	 * @param associado
	 * @return
	 */
	public Associado save(Associado associado);
	
	/**
	 * @param sequencial
	 */
	public void delete(Long sequencial);
	
}