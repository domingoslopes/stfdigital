package br.jus.stf.plataforma.pesquisas.interfaces.command;

import java.util.Map;

import org.hibernate.validator.constraints.NotEmpty;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author Lucas.Rodrigues
 *
 */
@ApiModel("Commando para pesquisar objetos nos índices")
public class PesquisarCommand {

	@ApiModelProperty("Os campos a serem retornados")
	private String[] campos;
	
	@ApiModelProperty("Índices para pesquisa")
	private String[] tipos;
	
	@ApiModelProperty(value = "Índices para pesquisa", required = true)
	private String[] indices;
	
	@NotEmpty
	@ApiModelProperty(value = "Filtros com campos e valores para realizar pesquisa", required = true)
	private Map<String, String[]> filtros;
	
	@ApiModelProperty("Campo para ordenar pesquisa")
	private Map<String, String> ordenadores;
	
	@ApiModelProperty("Página requisitada da pesquisa paginada")
	private Integer page = 0;
	
	@ApiModelProperty("Tamanho da pesquisa paginada")
	private Integer size = 15;
	
	@ApiModelProperty("Campo para agregação.")
	private String campoAgregacao;

	/**
	 * @return the campos
	 */
	public String[] getCampos() {
		return campos;
	}
	
	/**
	 * @return the tipos
	 */
	public String[] getTipos() {
		return tipos;
	}
	
	/**
	 * @return the indices
	 */
	public String[] getIndices() {
		return indices;
	}
	
	/**
	 * @return the filtros
	 */
	public Map<String, String[]> getFiltros() {
		return filtros;
	}

	/**
	 * @return the ordenadores
	 */
	public Map<String, String> getOrdenadores() {
		return ordenadores;
	}

	/**
	 * @return the page
	 */
	public Integer getPage() {
		return page;
	}

	/**
	 * @return the size
	 */
	public Integer getSize() {
		return size;
	}

	/**
	 * @return o campo
	 */
	public String getCampoAgregacao() {
		return this.campoAgregacao;
	}
}
