package br.jus.stf.processamentoinicial.suporte.domain;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import br.jus.stf.processamentoinicial.suporte.domain.model.Peca;

/**
 * Classe responsável por realizar a numeração de ordem de um conjunto
 * de peças.
 * 
 * @author Tomas.Godoi
 *
 */
public class ControladorOrdenacaoPecas {
	
	private final List<Peca> pecas;
	
	public ControladorOrdenacaoPecas(final List<Peca> pecas) {
		this.pecas = pecas;
		ordenarPecas();
	}

	public void ordenarPecas() {
		pecas.sort(comparatorPecasPeloNumeroOrdem());
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
		return pecas.size() > 0 ? pecas.get(pecas.size() - 1).numeroOrdem() : 0L;
	}
	
	/**
	 * Recupera o primeiro número de peça.
	 * 
	 * @return Primeiro número de ordem das peças.
	 */
	public Long primeiroNumeroOrdemPeca() {
		return pecas.size() > 0 ? pecas.get(0).numeroOrdem() : 0L;
	}

	/**
	 * Normaliza a ordenação de peças. Isso consiste
	 * em recalcular o número de ordem de acordo com a ordenação
	 * atual.
	 * 
	 */
	public void normalizarOrdenacaoPecas() {
		Long numeroOrdemAtual = 1L;
		for (Peca p : pecas) {
			p.numerarOrdem(numeroOrdemAtual);
			numeroOrdemAtual++;
		}
	}

	private Comparator<? super Peca> comparatorPecasPeloNumeroOrdem() {
		return (p1, p2) -> p1.numeroOrdem().compareTo(p2.numeroOrdem());
	}

	public boolean adicionarPeca(Peca peca) {
		numerarPeca(peca);
		return pecas.add(peca);
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
		if (novoNumeroOrdem > pecas.size()) {
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
		Iterator<Peca> iterator = pecas.iterator();
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
		ordenarPecas();
		return true;
	}

	public void substituirPeca(Peca pecaOriginal, Peca pecaSubstituta) {
		ListIterator<Peca> iterator = pecas.listIterator();
		while (iterator.hasNext()) {
			Peca pecaAtual = iterator.next();
			if (pecaAtual.equals(pecaOriginal)) {
				numerarPecaSubstituta(pecaAtual, pecaSubstituta);
				iterator.set(pecaSubstituta);
				break;
			}
		}
	}
	
}
