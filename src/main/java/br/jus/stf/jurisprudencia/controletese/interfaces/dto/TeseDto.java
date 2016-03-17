package br.jus.stf.jurisprudencia.controletese.interfaces.dto;

import java.util.List;

import br.jus.stf.processamentoinicial.suporte.interfaces.dto.AssuntoDto;

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
	
	@ApiModelProperty(value = "Tipo de tese")
	private String tipoTese;
	
	public TeseDto(Long codigo, String descricao, Long numero, List<AssuntoDto> assuntos, String tipoTese) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.numero = numero;
		this.assuntos = assuntos;
		this.tipoTese = tipoTese;
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
	
	public String getTipoTese() {
		return tipoTese;
	}

}
