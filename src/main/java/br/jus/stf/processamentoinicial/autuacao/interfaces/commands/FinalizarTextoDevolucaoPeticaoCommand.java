package br.jus.stf.processamentoinicial.autuacao.interfaces.commands;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Comando usado para finalizar a geração do texto do documento de devolução de uma petição.
 *  
 * @author Anderson.Araujo
 * @since 15.03.2016
 *
 */
@ApiModel("Comando usado para finalizar a geração do texto do documento de devolução de uma petição.")
public class FinalizarTextoDevolucaoPeticaoCommand {
	@ApiModelProperty("Id da petição")
	private Long peticaoId;
	
	@ApiModelProperty("Id do modelo de documento.")
	private Long modeloId;

	public Long getPeticaoId() {
		return peticaoId;
	}
	
	public Long getModeloId() {
		return modeloId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}