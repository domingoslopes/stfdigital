package br.jus.stf.processamentoinicial.suporte.domain.model;

import java.util.List;

/**
 * @author Rafael.Alencar
 */
public interface ProcedenciaGeograficaRepository {
	
	/**
	 * @param id
	 * @return
	 */
	public Pais findOnePais(Long pais);
	
	/**
	 * Recupera a lista de países
	 * 
	 * @return a lista de países
	 */
	public List<Pais> findAllPais();
	
	/**
	 * Pesquisa uma unidade da federação
	 * 
	 * @param Long sequencial
	 * @return
	 */
	public UnidadeFederacao findOneUnidadeFederacao(Long sequencial);
	
	/**
	 * Pesquisa todas as unidades da federação
	 * 
	 * @return lista de unidades da federação
	 */
	public List<UnidadeFederacao> findAllUnidadeFederacao();
	
	/**
	 * @param pais
	 * @return
	 */
	public List<UnidadeFederacao> findUnidadeFederacaoByPais(Pais pais);

}