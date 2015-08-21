package br.jus.stf.autuacao.interfaces.dto;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 19.08.2015
 */
public class ProcessoDistribuidoDto {
	
	private String numero;
	private String classe;
	private String relator;
	
	public ProcessoDistribuidoDto(String classe, String numero, String relator) {
		this.classe = classe;
		this.numero = numero;
		this.relator = relator;
	}
	
	public String getNumero() {
		return numero;
	}
	
	public String getClasse() {
		return classe;
	}
	
	public String getRelator() {
		return relator;
	}
	
}
