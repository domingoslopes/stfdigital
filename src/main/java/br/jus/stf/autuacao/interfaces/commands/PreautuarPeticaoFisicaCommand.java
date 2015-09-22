package br.jus.stf.autuacao.interfaces.commands;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Objeto usado para enviar os dados necessários para préautuar uma petição física.
 * 
 * @author Anderson.Araujo
 * 
 * @version 1.0.0
 * @since 15.09.2015
 *
 */
@ApiModel(value = "Contém as informações necessárias para préautuar a petição física recebida pelo Recebedor.")
public class PreautuarPeticaoFisicaCommand {

	@NotNull
	@ApiModelProperty(value = "Id da petição física registrada.", required=true)
	private Long peticaoId;
	
	@NotBlank
	@ApiModelProperty(value = "A classe processual sugerida pelo práutuador.", required=true)
	private String classeId;
	
	public Long getPeticaoId() {
		return peticaoId;
	}
	
	public String getClasseId() {
		return classeId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this); 
	}	
}

