package br.jus.stf.processamentoinicial.suporte.interfaces.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * DTO usado para transportar dados sobre juízo de origem.
 * 
 * @author Anderson.Araujo
 * @since 17.03.2016
 *
 */
@ApiModel("DTO usado para transportar dados sobre juízo de origem.")
public class JuizoOrigemDto {
	@ApiModelProperty("Código do juízo.")
	private Long codigo;
	
	@ApiModelProperty("Nome do juízo.")
	private String nome;
	
	public JuizoOrigemDto(Long codigo, String nome){
		this.codigo = codigo;
		this.nome = nome;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
