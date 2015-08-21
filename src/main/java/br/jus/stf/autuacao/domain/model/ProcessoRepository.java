package br.jus.stf.autuacao.domain.model;

import java.util.List;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:33:25
 */
public interface ProcessoRepository {

	/**
	 * 
	 * @param numeroProcesso
	 */
	public Processo find(ProcessoId numeroProcesso);

	public List<Processo> findAll();

	/**
	 * 
	 * @param processo
	 */
	public void store(Processo processo);

	public ProcessoId nextProcessoId();

}