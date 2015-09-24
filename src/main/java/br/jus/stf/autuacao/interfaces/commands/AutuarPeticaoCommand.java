package br.jus.stf.autuacao.interfaces.commands;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 23.06.2015
 */
@ApiModel(value = "Contém as informações necessárias para autuar a petição enviada pelo peticionador")
public class AutuarPeticaoCommand {

	@NotNull
	@ApiModelProperty(value = "Id da petição física registrada.", required=true)
	private Long peticaoId;
	
	@NotBlank
	@ApiModelProperty(value = "A classe processual definitiva, selecionada pelo autuador", required=true)
	private String classeId;
	
	@NotBlank
	@ApiModelProperty(value = "Contém o resultado da análise do autuador, indicando se a petição é válida ou não", required=true)
	private boolean valida;
	
	@ApiModelProperty(value = "Contém o motivo da recusa da petição, no caso de petições inválidas", required=true)
	private String motivo;
	
	public Long getPeticaoId() {
		return this.peticaoId;
	}
	
	public String getClasseId() {
		return classeId;
	}

	public boolean isValida() {
		return valida;
	}
	
	public String getMotivo() {
		return motivo;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this); 
	}
	
}
