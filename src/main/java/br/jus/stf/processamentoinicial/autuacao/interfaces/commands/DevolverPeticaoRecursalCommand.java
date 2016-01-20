package br.jus.stf.processamentoinicial.autuacao.interfaces.commands;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Comando para sinalizar ao BMP a devolução de uma petição recursal.
 * 
 * @author Anderson.Araujo
 * 
 * @since 19.01.2016
 *
 */
public class DevolverPeticaoRecursalCommand {
	@NotNull
	@ApiModelProperty(value = "Id da petição física registrada.", required=true)
	private Long peticaoId;
	
	@ApiModelProperty(value = "Contém o motivo da recusa da petição, no caso de petições indevidas", required=true)
	private String motivo;
	
	public Long getPeticaoId() {
		return peticaoId;
	}
	
	public String getMotivo() {
		return motivo;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this); 
	}
}
