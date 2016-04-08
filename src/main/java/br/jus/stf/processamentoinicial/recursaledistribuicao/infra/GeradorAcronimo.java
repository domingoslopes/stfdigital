package br.jus.stf.processamentoinicial.recursaledistribuicao.infra;

import java.util.StringTokenizer;

import org.springframework.stereotype.Component;

/**
 * Classe usada para gerar acrônimos.
 * 
 * @author anderson.araujo
 * @since 07.04.2016
 *
 */
@Component
public class GeradorAcronimo {
	/**
	 * Gera um acrônimo para o nome informado.
	 * @param nome
	 * @return Acrônimo.
	 */
	public String gerar(String nome) {
		if (nome == null) {
			return "";
		}
		
		StringBuffer acronimo = new StringBuffer();

		StringTokenizer tokens = new StringTokenizer(nome, " ");
		while (tokens.hasMoreElements()) {
			String acron = tokens.nextToken();
			acronimo.append(acron.charAt(0) + ".");
		}
		
		return acronimo.toString();
	}
}
