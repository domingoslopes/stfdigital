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
	public List<Tese> findTeseByTipo(TipoTese tipo);
	
	/**
	 * 
	 * @param tese
	 */
	public <T extends Tese> T save(Tese tese);

}