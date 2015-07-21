package br.jus.stf.autuacao.interfaces.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 20.07.2015
 */
@ApiModel(value = "Representa uma classe processual ativa")
public class ClasseDto {
	
	@ApiModelProperty(value = "A sigla da classe. Por exemplo: 'AP'.")
	private String sigla;

	@ApiModelProperty(value = "O nome da classe. Por exemplo: 'Ação Penal'.")
	private String nome;
	
	public ClasseDto(String sigla, String nome) {
		this.sigla = sigla;
		this.nome = nome;
	}
	
	public String getSigla() {
		return sigla;
	}
	
	public String getNome() {
		return nome;
	}

}
