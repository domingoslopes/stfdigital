package br.jus.stf.processamentoinicial.suporte.interfaces.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * DTO usado para transferir dados sobre unidades da federação.
 * 
 * @author Anderson.Araujo
 * @since 17.03.2016
 *
 */
@ApiModel("DTO usado para transferir dados sobre unidades da federação")
public class UnidadeFederacaoDto {
	@ApiModelProperty("Id da unidade da federação.")
	private Long id;
	
	@ApiModelProperty("Nome da unidade da federação.")
	private String nome;
	
	@ApiModelProperty("Sigla da unidade da federação.")
	private String sigla;
	
	public UnidadeFederacaoDto(Long id, String nome, String sigla){
		this.id = id;
		this.nome = nome;
		this.sigla = sigla;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
}
