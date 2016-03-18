package br.jus.stf.plataforma.pesquisas.interfaces.command;

import javax.validation.constraints.NotNull;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author Lucas.Rodrigues
 *
 */
@ApiModel("Comando para salvar uma pesquisa avançada")
public class ExcluirPesquisaAvancadaCommand {

	@ApiModelProperty("Id da consulta")
	@NotNull
	private Long pesquisaId;
	
	public Long getPesquisaId() {
		return pesquisaId;
	}
	
}
