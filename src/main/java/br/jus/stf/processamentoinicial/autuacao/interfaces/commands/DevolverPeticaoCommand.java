package br.jus.stf.processamentoinicial.autuacao.interfaces.commands;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

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
	
	@NotBlank
	@ApiModelProperty(value = "O número do ofício de devolução", required=true)
	private Long numeroOficio;
	
	@NotBlank
	@ApiModelProperty(value = "O código indicando o tipo de devolução", required=true)
	private String tipoDevolucao;
	
	@NotBlank
	@ApiModelProperty(value = "O documento do ofício de devolução em HTML", required = true)
	private String documento;
	
	public Long getPeticaoId() {
		return this.peticaoId;
	}
	
	public Long getNumeroOficio() {
		return numeroOficio;
	}
	
	public String getTipoDevolucao() {
		return tipoDevolucao;
	}
	
	public String getDocumento() {
		return documento;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this); 
	}
	
}
