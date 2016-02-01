package br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.commands;

import java.util.Map;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Comando responsável por enviar os dados da análise de pressupostos formais para o back-end.
 * 
 * @author Anderson.Araujo
 * 
 * @since 26.01.2016
 *
 */
@ApiModel(value = "Contém as informações necessárias para a análise de pressupostos formais do processo recursal.")
public class AnalisarPressupostosFormaisCommand {
	@NotNull
	@ApiModelProperty(value = "Id do processo recursal.", required=true)
	private Long processoId;
	
	@NotEmpty
	@ApiModelProperty(value = "Classificação da análise", required=true)
	private String classificacao;
	
	@NotEmpty
	@ApiModelProperty(value = "Lista com os motivos e suas respectivas observações.", required=true)
	private Map<Long, String> motivos;
	
	@ApiModelProperty(value = "Lista com os motivos e suas respectivas observações.", required=false)
	private String observacao;
		
	public Long getProcessoId() {
		return processoId;
	}

	public String getClassificacao() {
		return classificacao;
	}

	public Map<Long, String> getMotivos() {
		return motivos;
	}
	
	public String getObservacao() {
		return observacao;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this); 
	}
}
