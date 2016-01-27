/**
 * 
 */
package br.jus.stf.processamentoinicial.suporte.interfaces.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author Anderson.Araujo
 *
 */
@ApiModel(value = "Representa um assunto")
public class MotivoInaptidaoDto {
	
	@ApiModelProperty(value = "Id do motivo.")
	private Long id;
	
	@ApiModelProperty(value = "Descrição do motivo")
	private String descricao;
	
	public MotivoInaptidaoDto(Long id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
