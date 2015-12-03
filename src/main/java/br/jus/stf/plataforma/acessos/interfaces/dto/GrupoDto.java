package br.jus.stf.plataforma.acessos.interfaces.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Classe responsável por transportar dados de grupos de usuários para o front-end.
 * 
 * @author Anderson.Araujo
 * 
 * @since 02.12.2015
 *
 */
@ApiModel(value = "Classe responsável por transportar dados de grupos de usuários para o front-end.")
public class GrupoDto {
	
	@ApiModelProperty("Id do grupo de usuários.")
	private Long id;
	
	@ApiModelProperty("Nome do grupo de usuários.")
	private String nome;
	
	public GrupoDto(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
