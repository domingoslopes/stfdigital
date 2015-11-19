package br.jus.stf.processamentoinicial.autuacao.interfaces.commands;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Objeto usado para enviar os dados necessários para préautuar uma petição física.
 * 
 * @author Anderson Araújo
 * @author Rodrigo Barreiros
 * 
 * @version 1.0.0
 * @since 15.09.2015
 */
@ApiModel(value = "Contém as informações necessárias para préautuar a petição física recebida pelo Recebedor.")
public class PreautuarPeticaoFisicaCommand {

	@NotBlank
	@ApiModelProperty(value = "A classe processual sugerida pelo práutuador.", required=true)
	private String classeId;
	
	@NotBlank
	@ApiModelProperty(value = "Contém o resultado da análise do pré-autuador, indicando se a petição está 'Correta' ou 'Indevida'", required=true)
	private boolean valida;
	
	@ApiModelProperty(value = "Contém o motivo da recusa da petição, no caso de petições indevidas", required=true)
	private String motivo;
	
	public void setClasseId(String classeId) {
		this.classeId = classeId;
	}
	
	public String getClasseId() {
		return classeId;
	}
	
	public void setValida(boolean valida) {
		this.valida = valida;
	}
	
	public boolean isValida() {
		return valida;
	}
	
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	
	public String getMotivo() {
		return motivo;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this); 
	}	
	
}

