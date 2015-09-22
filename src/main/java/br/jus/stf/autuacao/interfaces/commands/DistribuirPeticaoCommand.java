package br.jus.stf.autuacao.interfaces.commands;

import javax.validation.constraints.NotNull;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 20.07.2015
 */
@ApiModel(value = "Contém as informações necessárias para distribuir uma nova petição")
public class DistribuirPeticaoCommand {
	
	@NotNull
	@ApiModelProperty(value = "Id da petição física registrada.", required=true)
	private Long peticaoId;
	
	@NotNull
	@ApiModelProperty(value = "O ministro sorteado para relatar o processo resultado da petição recebida", required=true)
	private Long ministroId;

	public Long getPeticaoId() {
		return this.peticaoId;
	}
	
	public Long getMinistroId() {
		return ministroId;
	}
}
