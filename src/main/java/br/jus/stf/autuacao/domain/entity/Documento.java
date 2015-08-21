/**
 * 
 */
package br.jus.stf.autuacao.domain.entity;

/**
 * Representa um documento anexado à uma petição.
 * 
 * @author Anderson.Araujo
 * @version 1.0.0
 * @since 23.07.2015
 *
 */
public class Documento {
	private String descricao;

	public Documento() {
		
	}
	
	public Documento(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
