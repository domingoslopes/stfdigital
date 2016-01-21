package br.jus.stf.processamentoinicial.autuacao.interfaces.commands;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Comando de confirmação da devolução de uma petição física recursal.
 * 
 * @author Anderson.Araujo
 * 
 * @since 19.01.2016
 *
 */
@ApiModel(value = "Contém as informações necessárias para gerar a peça de devolução que anexada à petição devolvida")
public class ConfirmarDevolucaoCommand {
	@NotNull
	@ApiModelProperty(value = "Id da petição física registrada.", required=true)
	private Long peticaoId;
	
	@NotBlank
	@ApiModelProperty(value = "O número do ofício de devolução", required=true)
	private Long numeroOficio;
	
	@NotBlank
	@ApiModelProperty(value = "O código indicando o tipo de devolução", required=true)
	private String tipoDevolucao;
	
	public Long getPeticaoId() {
		return this.peticaoId;
	}
	
	public Long getNumeroOficio() {
		return numeroOficio;
	}
	
	public String getTipoDevolucao() {
		return tipoDevolucao;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this); 
	}
}
