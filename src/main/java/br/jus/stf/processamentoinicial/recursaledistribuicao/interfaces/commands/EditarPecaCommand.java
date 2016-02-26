package br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.commands;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Usado para enviar dados da peça editada para o back-end.
 * @author Anderson.Araujo
 *
 */
@ApiModel(value = "Usado para enviar dados da peça editada para o back-end.")
public class EditarPecaCommand {
	@ApiModelProperty(value = "Id da peça", required=true)
	private Long pecaId;
	
	@ApiModelProperty(value = "Id do tipo de peça.", required=true)
	private Long tipoPecaId;
	
	@ApiModelProperty(value = "Descrição da peça.", required=true)
	private String descricao;
	
	@ApiModelProperty(value = "Nº de ordem da peça dentro do processo.", required=true)
	private Long numeroOrdem;
	
	@ApiModelProperty(value = "Nº de ordem da peça dentro do processo.", required=true)
	private String visibilidade;
	
	public Long getPecaId(){
		return pecaId;
	}
	
	public Long getTipoPecaId(){
		return tipoPecaId;
	}
	
	public String getDescricao(){
		return descricao;
	}
	
	public Long getNumeroOrdem(){
		return numeroOrdem;
	}
	
	public String getVisibilidade(){
		return visibilidade;
	}
}
