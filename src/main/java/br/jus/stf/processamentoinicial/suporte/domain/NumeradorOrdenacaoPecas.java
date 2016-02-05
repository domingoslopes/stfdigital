package br.jus.stf.processamentoinicial.suporte.domain;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

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
		Iterator<Peca> iterator = pecas.stream().sorted(comparatorPecasPeloNumeroOrdem()).iterator();
		Long numeroOrdemAtual = 1L;
		while (iterator.hasNext()) {
			iterator.next().numerarOrdem(numeroOrdemAtual);
			numeroOrdemAtual++;
		}
	}

	private Comparator<? super Peca> comparatorPecasPeloNumeroOrdem() {
		return (p1, p2) -> p1.numeroOrdem().compareTo(p2.numeroOrdem());
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
	
	/**
	 * Reordena uma peça para o novo número de ordem, caso seja possível.
	 * 
	 * @param peca
	 * @param novoNumeroOrdem
	 * @return true se conseguiu reordenar, false caso contrário
	 */
	public boolean reordenarPeca(Peca peca, Long novoNumeroOrdem) {
		if (!pecas.contains(peca)) {
			return false; // Peça não está na coleção.
		}
		if (!pecas.stream().anyMatch(p -> p.numeroOrdem().equals(novoNumeroOrdem))) {
			return false; // Não possui um item com o novo número de ordem.
		}
		return reordenarPecas(Arrays.asList(peca), novoNumeroOrdem);
	}

	/**
	 * Reordena as peças para a ordem especificada, ajustando os números de ordem das outras peças.
	 * 
	 * @param pecasOrdenacao
	 * @param numeroOrdem
	 * @return
	 */
	public boolean reordenarPecas(List<Peca> pecasOrdenacao, Long numeroOrdem) {
		Iterator<Peca> iterator = pecas.stream().sorted(comparatorPecasPeloNumeroOrdem()).iterator();
		Long numeroOrdemAtual = 1L;
		Long numeroOrdemFinal = new Long(pecas.size());
		while (numeroOrdemAtual <= numeroOrdemFinal) {
			if (numeroOrdemAtual == numeroOrdem) { // Numera as peças a partir do novo número de ordem.
				for (Peca p : pecasOrdenacao) {
					p.numerarOrdem(numeroOrdemAtual);
					numeroOrdemAtual++;
				}
			} else { // Numera as outras peças na sequência.
				Peca pecaAtual = iterator.next();
				if (!pecasOrdenacao.contains(pecaAtual)) {
					pecaAtual.numerarOrdem(numeroOrdemAtual);
					numeroOrdemAtual++;
				}
			}
		}
		return true;
	}
	
}
