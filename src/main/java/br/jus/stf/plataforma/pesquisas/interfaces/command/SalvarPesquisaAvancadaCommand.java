package br.jus.stf.plataforma.pesquisas.interfaces.command;

import org.hibernate.validator.constraints.NotBlank;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;


/**
 * @author Lucas.Rodrigues
 *
 */
@ApiModel("Comando para salvar uma pesquisa avançada")
public class SalvarPesquisaAvancadaCommand {
	
	@ApiModelProperty("Nome da consulta")
	@NotBlank
	private String nome;
	
	@ApiModelProperty("Consulta a ser salva")
	@NotBlank
	private String consulta;
	
	public String getNome() {
		return nome;
	}
	
	public String getConsulta() {
		return consulta;
	}
	
}
