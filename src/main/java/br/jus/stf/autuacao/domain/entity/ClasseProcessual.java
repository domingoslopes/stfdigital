/**
 * 
 */
package br.jus.stf.autuacao.domain.entity;

/**
 * @author Anderson.Araujo
 *
 */
public class ClasseProcessual {
	private String sigla;

	private String nome;
	
	public ClasseProcessual(String sigla) {
		this.sigla = sigla;
	}
	
	public ClasseProcessual(String sigla, String nome) {
		this.sigla = sigla;
		this.nome = nome;
	}
	
	public String getSigla() {
		return sigla;
	}
	
	public String getNome() {
		return nome;
	}
}
