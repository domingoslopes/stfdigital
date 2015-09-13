package br.jus.stf.autuacao.interfaces.dto;


/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 19.08.2015
 */
public class ProcessoDistribuidoDto {
	
	private Long numero;
	private String classe;
	private Long relator;
	
	public ProcessoDistribuidoDto(String classe, Long numero, Long relator) {
		this.classe = classe;
		this.numero = numero;
		this.relator = relator;
	}
	
	public Long getNumero() {
		return numero;
	}
	
	public String getClasse() {
		return classe;
	}
	
	public Long getRelator() {
		return relator;
	}
	
}
