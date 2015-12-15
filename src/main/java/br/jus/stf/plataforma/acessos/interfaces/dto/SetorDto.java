package br.jus.stf.plataforma.acessos.interfaces.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Classe responsável por transportar os dados de setor do back-end para o front-end.")
public class SetorDto {
	
	@ApiModelProperty(value="Código do setor")
	private Long codigo;
	
	@ApiModelProperty(value="Nome do setor")
	private String nome;
	
	@ApiModelProperty(value="Sigla do setor")
	private String sigla;

	public SetorDto(Long codigo, String nome, String sigla) {
		this.codigo = codigo;
		this.nome = nome;
		this.sigla = sigla;
	}
	
	public Long getCodigo() {
		return codigo;
	}

	public String getNome() {
		return nome;
	}

	public String getSigla() {
		return sigla;
	}

}
