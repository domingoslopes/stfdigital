package br.jus.stf.plataforma.dashboards.interfaces.dto;

/**
 * @author Lucas.Rodrigues
 *
 */
public class DashletDto {

	private String nome;
	private String descricao;
	
	public DashletDto(String nome, String descricao) {
		this.nome = nome;
		this.descricao = descricao;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}
