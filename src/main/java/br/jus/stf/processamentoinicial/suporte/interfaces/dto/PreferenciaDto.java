package br.jus.stf.processamentoinicial.suporte.interfaces.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author Rafael Alencar
 * 
 * @since 13.01.2016
 */
@ApiModel(value = "Representa uma preferência ativa")
public class PreferenciaDto {
	
	@ApiModelProperty(value = "O código da preferência.")
	private Long codigo;

	@ApiModelProperty(value = "A descrição da preferência.")
	private String descricao;
	
	public PreferenciaDto(Long codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public Long getCodigo() {
		return codigo;
	}
	
	public String getDescricao() {
		return descricao;
	}

}
