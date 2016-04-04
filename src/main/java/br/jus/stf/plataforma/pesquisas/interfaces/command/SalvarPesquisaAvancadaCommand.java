package br.jus.stf.plataforma.pesquisas.interfaces.command;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;


/**
 * @author Lucas.Rodrigues
 *
 */
@ApiModel("Comando para salvar uma pesquisa avançada")
public class SalvarPesquisaAvancadaCommand {
	
	@ApiModelProperty("Id da pesquisa")
	private Long pesquisaId;
	
	@ApiModelProperty("Nome da pesquisa")
	@NotBlank
	private String nome;
	
	@ApiModelProperty("Tipo da pesquisa")
	@NotBlank
	private String tipo;
	
	@ApiModelProperty("Consulta a ser salva")
	@NotBlank
	private String consulta;
	
	@ApiModelProperty("Indíces para a pesquisa")
	@NotEmpty
	private String[] indices;
	
	public Long getPesquisaId() {
		return pesquisaId;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public String getConsulta() {
		return consulta;
	}
	
	public String[] getIndices() {
		return indices;
	}
	
}
