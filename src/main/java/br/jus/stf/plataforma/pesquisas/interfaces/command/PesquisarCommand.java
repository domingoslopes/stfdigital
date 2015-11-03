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
	private Map<String, String> filtros;
	
	@ApiModelProperty("Campo para ordenar pesquisa")
	private Map<String, String> ordenadores;
	
	@ApiModelProperty("Página requisitada da pesquisa paginada")
	private Integer pagina = 0;
	
	@ApiModelProperty("Tamanho da pesquisa paginada")
	private Integer tamanho = 15;

	/**
	 * @return the campos
	 */
	public String[] getCampos() {
		return campos;
	}
	
	/**
	 * @param campos the campos to set
	 */
	public void setCampos(String[] campos) {
		this.campos = campos;
	}
	
	/**
	 * @return the tipos
	 */
	public String[] getTipos() {
		return tipos;
	}
	
	/**
	 * @param tipos the tipos to set
	 */
	public void setTipos(String[] tipos) {
		this.tipos = tipos;
	}
	
	/**
	 * @return the indices
	 */
	public String[] getIndices() {
		return indices;
	}
	
	/**
	 * @param indices the indices to set
	 */
	public void setIndices(String[] indices) {
		this.indices = indices;
	}
	
	/**
	 * @return the filtros
	 */
	public Map<String, String> getFiltros() {
		return filtros;
	}
	
	/**
	 * @param filtros the filtros to set
	 */
	public void setFiltros(Map<String, String> filtros) {
		this.filtros = filtros;
	}

	/**
	 * @return the ordenadores
	 */
	public Map<String, String> getOrdenadores() {
		return ordenadores;
	}

	/**
	 * @param ordenadores the ordenadores to set
	 */
	public void setOrdenadores(Map<String, String> ordenadores) {
		this.ordenadores = ordenadores;
	}

	/**
	 * @return the pagina
	 */
	public Integer getPagina() {
		return pagina;
	}

	/**
	 * @param pagina the pagina to set
	 */
	public void setPagina(Integer pagina) {
		this.pagina = pagina;
	}

	/**
	 * @return the tamanho
	 */
	public Integer getTamanho() {
		return tamanho;
	}

	/**
	 * @param tamanho the tamanho to set
	 */
	public void setTamanho(Integer tamanho) {
		this.tamanho = tamanho;
	}

}
