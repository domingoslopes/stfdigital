package br.jus.stf.processamentoinicial.autuacao.domain;

import br.jus.stf.processamentoinicial.autuacao.domain.model.TipoDevolucao;

/**
 * Representa a classe de geração automática do ofício para devolução das petições "indevidas", 
 * aquelas que chegaram ao STF indevidamente.
 * 
 * @author Rodrigo Barreiros
 *
 * @since 1.0.0.M3
 * @since 09.10.2015
 * 
 * @see TipoDevolucao
 */
public interface PecaDevolucaoBuilder {
	
	/**
	 * Gera o Ofício de Devolução, para uma dada petição, de acordo com o Tipo de Devolução.
	 * 
	 * <p>O cliente deve fornecer também o número do ofício, que será controlado 
	 * manualmente pelo usuário.
	 *  
	 * @param peticao a petição que será devolvida
	 * @param tipoDevolucao o tipo de devolução
	 * @param numero o número do ofício
	 * @return o ofício geral
	 */
	byte[] build(String identificacao, TipoDevolucao tipoDevolucao, Long numero);

}
