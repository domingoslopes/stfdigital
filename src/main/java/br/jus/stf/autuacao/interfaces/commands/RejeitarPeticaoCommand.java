package br.jus.stf.autuacao.interfaces.commands;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Objeto usado para transportar informações sobre a rejeição de uma petição.
 * 
 * @author Anderson.Araujo
 * 
 * @version 1.0.0
 * @since 16.09.2015
 *
 */
@ApiModel(value = "Contém as informações sobre a rejeição da petição por parte do autuador.")
public class RejeitarPeticaoCommand {
	@NotBlank
	@ApiModelProperty(value = "O motivo da rejeição da petição alegado pelo autuador.", required=true)
	private String motivoRejeicao;
	
	public String getMotivoRejeicao() {
		return this.motivoRejeicao;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this); 
	}
}
