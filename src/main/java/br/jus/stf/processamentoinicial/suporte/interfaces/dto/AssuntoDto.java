package br.jus.stf.processamentoinicial.suporte.interfaces.dto;

import java.util.ArrayList;
import java.util.List;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Classe usada para transportar dados de motivo de inaptidão de processo recursal do front-end para o back-end.
 * 
 * @author Anderson.Araujo
 * 
 * @since 27.01.2016
 *
 */
@ApiModel(value = "Representa um assunto")
public class AssuntoDto {
	
	@ApiModelProperty(value = "Código do assunto")
	private String codigo;
	
	@ApiModelProperty(value = "Descrição do assunto")
	private String descricao;
	
	@ApiModelProperty(value = "Assunto pai.")
	private AssuntoDto assuntoPai;
	
	@ApiModelProperty(value = "Assuntos vinculados.")
	private List<AssuntoDto> assuntosFilhos = new ArrayList<AssuntoDto>();
	
	public AssuntoDto(){
		
	}
	
	public AssuntoDto(String codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public AssuntoDto(String codigo, String descricao, AssuntoDto assuntoPai) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.assuntoPai = assuntoPai;
	}
	
	public String getCodigo() {
		return codigo;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public AssuntoDto getAssuntoPai() {
		return assuntoPai;
	}
	
	public List<AssuntoDto> getAssuntosFilhos() {
		return assuntosFilhos;
	}
}
