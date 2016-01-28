package br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.commands;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Comando responsável por enviar os dados da revisão de pressupostos formais para o back-end.
 * 
 * @author Anderson.Araujo
 * 
 * @since 27.01.2016
 *
 */
@ApiModel(value = "Contém as informações necessárias para a análise de repercussão geral.")
public class RevisarRepercussaoGeralCommand {
	@NotNull
	@ApiModelProperty(value = "Id do processo recursal.", required=true)
	private Long processoId;
	
	@NotEmpty
	@ApiModelProperty(value = "Lista com os assuntos", required=true)
	private List<String> assuntos;
	
	@NotEmpty
	@ApiModelProperty(value = "Lista de teses", required=true)
	private List<Long> teses;
	
	public Long getProcessoId() {
		return processoId;
	}
	
	public List<String> getAssuntos() {
		return assuntos;
	}
	
	public List<Long> getTeses() {
		return teses;
	}
}