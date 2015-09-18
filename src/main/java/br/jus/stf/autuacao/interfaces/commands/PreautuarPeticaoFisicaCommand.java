package br.jus.stf.autuacao.interfaces.commands;

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

	@NotBlank
	@ApiModelProperty(value = "A classe processual sugerida pelo práutuador.", required=true)
	private String classeSugerida;
	
	public String getClasseSugerida() {
		return this.classeSugerida;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this); 
	}	
}

