/**
 * 
 */
package br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.dto;

/**
 * Objeto usado para enviar dados de status de processo.
 * 
 * @author Anderson.Araujo
 * 
 * @since 14.12.2015
 *
 */
public class ProcessoStatusDto {
	
	private String nome;
	private String descricao;

	public ProcessoStatusDto(String nome, String descricao) {
		this.nome = nome;
		this.descricao = descricao;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
