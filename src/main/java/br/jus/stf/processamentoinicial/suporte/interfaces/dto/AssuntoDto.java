package br.jus.stf.processamentoinicial.suporte.interfaces.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Classe usada para transportar dados de assuntos, do front-end para o back-end.
 * 
 * @author Anderson.Araujo
 * 
 * @since 25.01.2016
 *
 */
@ApiModel(value = "Representa um assunto")
public class AssuntoDto {
	
	@ApiModelProperty(value = "Código do assunto")
	private String codigo;
	
	@ApiModelProperty(value = "Descrição do assunto")
	private String descricao;
	
	public AssuntoDto(String codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public String getCodigo() {
		return codigo;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
