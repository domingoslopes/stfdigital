package br.jus.stf.jurisprudencia.controletese.domain.model;

import java.util.List;

import br.jus.stf.shared.TeseId;



/**
 * @author Rafael.Alencar
 *
 */
public interface TeseRepository {
	
	/**
	 * @para id
	 * @return a tese
	 */
	public Tese findOne(TeseId id);
	
	/**
	 * @return a lista de teses
	 */
	public List<Tese> findAll();
	
	/**
	 * @return a lista de assuntos para o tipo de tese
	 */
	public List<Tese> findTeseByTipo(TipoTese tipo, Long numero);

	/**
	 * Recupera as teses com os ids
	 * 
	 * @param tesesIds
	 * @return A lista de teses
	 */
	public List<Tese> findTesesByIds(List<TeseId> tesesIds);
}