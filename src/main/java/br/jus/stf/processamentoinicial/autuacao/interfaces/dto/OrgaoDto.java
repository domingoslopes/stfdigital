package br.jus.stf.processamentoinicial.autuacao.interfaces.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author Rafael Alencar
 */
@ApiModel(value = "Representa o órgão que será apresentado ao usuário")
public class OrgaoDto {

	@ApiModelProperty(value = "Id do órgão")
	private Long id;
	
	@ApiModelProperty(value = "Nome do órgão")
	private String nome;
	
	public OrgaoDto(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}
	
}
