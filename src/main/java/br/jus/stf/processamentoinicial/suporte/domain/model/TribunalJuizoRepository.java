package br.jus.stf.processamentoinicial.suporte.domain.model;

import java.util.List;

/**
 * @author Rafael.Alencar
 */
public interface TribunalJuizoRepository {
	
	/**
	 * @param sequencial
	 * @return
	 */
	public TribunalJuizo findOne(Long sequencial);
	
	/**
	 * @return listas de tribunais e juízos
	 */
	public List<TribunalJuizo> findAll();
	
	/**
	 * @param uf
	 * @return lista de tribunais e juízos de uma unidade da federação
	 */
	public List<TribunalJuizo> findByUnidadeFederacao(UnidadeFederacao uf);

}