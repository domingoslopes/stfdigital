package br.jus.stf.processamentoinicial.autuacao.interfaces.dto;

/**
 * Representa a peça que será apresentada ao usuário.
 * 
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0.M3
 * @since 02.10.2015
 */
public class PecaDto {

	private String tipo;
	
	private String descricao;
	
	public PecaDto(String tipo, String descricao) {
		this.tipo = tipo;
		this.descricao = descricao;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}
