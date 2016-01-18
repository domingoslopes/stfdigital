package br.jus.stf.jurisprudencia.controletese.domain.model;

import java.util.List;

import br.jus.stf.shared.AssuntoId;



/**
 * @author Rafael.Alencar
 *
 */
public interface AssuntoRepository {
	
	/**
	 * @para id
	 * @return o assunto
	 */
	public Assunto findOne(AssuntoId id);
	
	/**
	 * @return a lista de assuntos
	 */
	public List<Assunto> findAll();
	
	/**
	 * 
	 * @param assunto
	 */
	public <T extends Assunto> T save(Assunto assunto);

}