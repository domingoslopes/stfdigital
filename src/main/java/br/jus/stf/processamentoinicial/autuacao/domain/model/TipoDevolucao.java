package br.jus.stf.processamentoinicial.autuacao.domain.model;

/**
 * Representa a forma de devolução de uma petição física que, em tempo de pré-autuação,
 * foi analisada como "indevida", ou seja, uma petição que deverá ser devolvida
 * por algum motivo específico.
 * 
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0.M3
 * @since 09.10.2015
 */
public enum TipoDevolucao {

	REMESSA_INDEVIDA("Remessa Indevida"),
	
	TRANSITADO("Transitado"),
	
	BAIXADO("Baixado");
	
	private String nome;
	
	private TipoDevolucao(String nome) {
		this.nome = nome;
	}
	
	public String nome() {
		return this.nome;
	}
	
}
