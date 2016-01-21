package br.jus.stf.processamentoinicial.autuacao.interfaces.commands;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Objeto usado para enviar os dados necessários para préautuar uma petição física.
 * 
 * @author Anderson.Araujo
 * @author Rodrigo Barreiros
 * 
 * @version 1.0.0
 * @since 15.09.2015
 */
@ApiModel(value = "Contém as informações necessárias para préautuar a petição física recebida pelo Recebedor.")
public class PreautuarPeticaoFisicaCommand {

	@NotNull
	@ApiModelProperty(value = "Id da petição física registrada.", required=true)
	private Long peticaoId;
	
	@NotBlank
	@ApiModelProperty(value = "A classe processual sugerida pelo práutuador.", required=true)
	private String classeId;
	
	@NotBlank
	@ApiModelProperty(value = "Contém o resultado da análise do pré-autuador, indicando se a petição está 'Correta' ou 'Indevida'", required=true)
	private boolean valida;
	
	@ApiModelProperty(value = "Contém o motivo da recusa da petição, no caso de petições indevidas", required=true)
	private String motivo;
	
	public Long getPeticaoId() {
		return peticaoId;
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

