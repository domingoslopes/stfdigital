package br.jus.stf.plataforma.identidades.interfaces.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author Lucas.Rodrigues
 *
 */
@ApiModel(value = "Representa uma pessoa")
public class PessoaDto {

	@ApiModelProperty(value = "O identificador da pessoa")
	private Long id;

	@ApiModelProperty(value = "O nome da pessoa")
	private String nome;
	
	public PessoaDto(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}
	
}
