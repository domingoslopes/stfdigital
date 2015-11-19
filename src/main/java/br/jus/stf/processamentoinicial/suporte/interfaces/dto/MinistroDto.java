package br.jus.stf.processamentoinicial.suporte.interfaces.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 20.07.2015
 */
@ApiModel(value = "Representa uma ministro ativo")
public class MinistroDto {
	
	@ApiModelProperty(value = "O código do ministro")
	private String codigo;
	
	@ApiModelProperty(value = "O nome do ministro")
	private String nome;
	
	public MinistroDto(String codigo, String nome) {
		this.codigo = codigo;
		this.nome = nome;
	}

	public String getCodigo() {
		return codigo;
	}
	
	public String getNome() {
		return nome;
	}

}
