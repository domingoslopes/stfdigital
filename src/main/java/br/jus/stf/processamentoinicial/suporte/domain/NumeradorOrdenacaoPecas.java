package br.jus.stf.processamentoinicial.suporte.domain;

import java.util.Collection;
import java.util.Iterator;

import br.jus.stf.processamentoinicial.suporte.domain.model.Peca;

/**
 * Classe responsável por realizar a numeração de ordem de um conjunto
 * de peças.
 * 
 * @author Tomas.Godoi
 *
 */
public class NumeradorOrdenacaoPecas {
	
	private final Collection<Peca> pecas;
	
	public NumeradorOrdenacaoPecas(final Collection<Peca> pecas) {
		this.pecas = pecas;
	}

	/**
	 * Recupera o próximo número de ordem de peça.
	 * 
	 * @return
	 */
	public Long proximoNumeroOrdemPeca() {
		return ultimoNumeroOrdemPeca() + 1;
	}
	
	/**
	 * Recupera o último número de peça.
	 * 
	 * @return
	 */
	public Long ultimoNumeroOrdemPeca() {
		return pecas.stream().mapToLong(p -> p.numeroOrdem()).max().orElse(0L);
	}

	/**
	 * Normaliza a ordenação de peças. Isso consiste
	 * em recalcular o número de ordem de acordo com a ordenação
	 * atual.
	 * 
	 */
	public void normalizarOrdenacaoPecas() {
		Iterator<Peca> iterator = pecas.stream().sorted((p1, p2) -> p1.numeroOrdem().compareTo(p2.numeroOrdem())).iterator();
		Long numeroOrdemAtual = 1L;
		while (iterator.hasNext()) {
			iterator.next().numerarOrdem(numeroOrdemAtual);
			numeroOrdemAtual++;
		}
	}

	/**
	 * Realiza a numeração da peça utilizando o próximo número de ordem.
	 * 
	 * @param peca
	 */
	public void numerarPeca(Peca peca) {
		peca.numerarOrdem(proximoNumeroOrdemPeca());
	}

	/**
	 * Numera uma peça substituta de outra.
	 * 
	 * @param pecaOriginal
	 * @param pecaSubstituta
	 */
	public void numerarPecaSubstituta(Peca pecaOriginal, Peca pecaSubstituta) {
		pecaSubstituta.numerarOrdem(pecaOriginal.numeroOrdem());
	}
	
	
}
