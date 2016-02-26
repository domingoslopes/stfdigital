package br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.commands;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Comando usado para realizar a juntada de uma peça ao processo.
 * 
 * @author Anderson.Araujo
 * @since 26.02.2016
 *
 */
@ApiModel(value = "Comando usado para realizar a juntada de uma peça ao processo.")
public class JuntarPecaCommand {
	@ApiModelProperty(value = "Id do processo.", required = true)
	private Long processoId;
	
	@ApiModelProperty(value = "Id da peça a ser juntada.", required = true)
	private Long pecaId;
	
	public Long getProcessoId(){
		return processoId;
	}
	
	public Long getPecaId(){
		return pecaId;
	}
}
