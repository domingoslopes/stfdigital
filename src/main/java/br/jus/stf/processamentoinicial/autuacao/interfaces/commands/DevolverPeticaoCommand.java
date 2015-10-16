package br.jus.stf.processamentoinicial.autuacao.interfaces.commands;

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

	@NotBlank
	@ApiModelProperty(value = "O número do ofício de devolução", required=true)
	private Long numeroOficio;
	
	@NotBlank
	@ApiModelProperty(value = "O código indicando o tipo de devolução", required=true)
	private String tipoDevolucao;
	
	public void setNumeroOficio(Long numeroOficio) {
		this.numeroOficio = numeroOficio;
	}
	
	public Long getNumeroOficio() {
		return numeroOficio;
	}
	
	public void setTipoDevolucao(String tipoDevolucao) {
		this.tipoDevolucao = tipoDevolucao;
	}
	
	public String getTipoDevolucao() {
		return tipoDevolucao;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this); 
	}
	
}
