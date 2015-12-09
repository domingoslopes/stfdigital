package br.jus.stf.plataforma.acessos.interfaces.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Classe responsável por transportar dados de um papél de usuário do back-end para o front-end.
 * 
 * @author Anderson.Araujo
 * 
 * @since 27.11.2015
 *
 */
@ApiModel(value="Objeto responsável por transportar dados de um papél de usuário do back-end para o front-end.")
public class PapelDto {
	
	@ApiModelProperty(value="Id do papel do usuário.", required=true)
	private Long id;
	
	@ApiModelProperty(value="Nome do papel do usuário.", required=true)
	private String nome;
	
	@ApiModelProperty(value="Nome do setor associado ao papel.", required=true)
	private String setor;
	
	public PapelDto(Long id, String nome, String setor) {
		this.id = id;
		this.nome = nome;
		this.setor = setor;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSetor() {
		return this.setor;
	}

	public void setSetor(String setor) {
		this.setor = setor;
	}
	
}
