package br.jus.stf.processamentoinicial.suporte.interfaces.dto;

import java.util.List;

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
	
	@ApiModelProperty(value = "Lista de assuntos vinculados à tese")
	private List<AssuntoDto> assuntos;
	
	public TeseDto(Long codigo, String descricao, Long numero, List<AssuntoDto> assuntos) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.numero = numero;
		this.assuntos = assuntos;
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
	
	public List<AssuntoDto> getAssuntos(){
		return assuntos;
	}
}
