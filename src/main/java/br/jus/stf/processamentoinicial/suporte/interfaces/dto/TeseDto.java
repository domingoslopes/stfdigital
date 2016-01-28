package br.jus.stf.processamentoinicial.suporte.interfaces.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Classe usada para transportar dados de motivo de inaptidão de processo recursal do front-end para o back-end.
 * 
 * @author Anderson.Araujo
 * 
 * @since 28.01.2016
 *
 */
@ApiModel(value = "Representa uma tese")
public class TeseDto {
	@ApiModelProperty(value = "Codigo da tese")
	private Long codigo;
	
	@ApiModelProperty(value = "Descrição da tese")
	private String descricao;
	
	@ApiModelProperty(value = "Nº da tese")
	private Long numero;
	
	public TeseDto(Long codigo, String descricao, Long numero) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.numero = numero;
	}
	
	public Long getCodigo() {
		return codigo;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public Long getNumero() {
		return numero;
	}
}
