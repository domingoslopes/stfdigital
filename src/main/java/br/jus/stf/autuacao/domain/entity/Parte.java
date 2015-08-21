/**
 * 
 */
package br.jus.stf.autuacao.domain.entity;

/**
 * Representa um do integrante de uma lide.
 * @author Anderson.Araujo
 *
 */
public class Parte {

	private String nome;
	
	public Parte() {
		
	}
	
	public Parte(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
