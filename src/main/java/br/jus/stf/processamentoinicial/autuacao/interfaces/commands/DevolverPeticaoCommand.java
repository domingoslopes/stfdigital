package br.jus.stf.processamentoinicial.autuacao.interfaces.commands;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 13.10.2015
 */
@ApiModel(value = "Contém as informações necessárias para gerar a peça de devolução que anexada à petição devolvida")
public class DevolverPeticaoCommand {

	@NotNull
	@ApiModelProperty(value = "Id da petição física registrada.", required=true)
	private Long peticaoId;
	
	@NotNull
	@ApiModelProperty(value = "O número do ofício de devolução", required=true)
	private Long numeroOficio;
	
	@NotNull
	@ApiModelProperty(value = "O código indicando o motivo da devolução", required=true)
	private Long motivoDevolucao;
	
	public Long getPeticaoId() {
		return this.peticaoId;
	}
	
	public Long getNumeroOficio() {
		return numeroOficio;
	}
	
	public Long getMotivoDevolucao() {
		return motivoDevolucao;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this); 
	}
	
}
