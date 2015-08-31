package br.jus.stf.autuacao.domain.model;

import java.util.List;

import br.jus.stf.shared.domain.model.PeticaoId;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:33:25
 */
public interface PeticaoRepository {

	/**
	 * 
	 * @param numeroPeticao
	 */
	public Peticao find(PeticaoId numeroPeticao);

	/**
	 * 
	 * @param status
	 */
	public List<Peticao> findStatus(final PeticaoStatus status);

	/**
	 * 
	 * @param peticao
	 */
	public void store(Peticao peticao);

	public Long nextNumero();

}