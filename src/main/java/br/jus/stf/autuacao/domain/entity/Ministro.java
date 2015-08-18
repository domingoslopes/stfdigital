/**
 * 
 */
package br.jus.stf.autuacao.domain.entity;

/**
 * @author Anderson.Araujo
 *
 */
public class Ministro {
	private int id;
	private String nome;

	public Ministro(int id, String nome){
		this.id = id;
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
