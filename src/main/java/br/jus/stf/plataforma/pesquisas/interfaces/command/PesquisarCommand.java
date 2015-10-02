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

	@ApiModelProperty(value = "Índices para pesquisa", required = false)
	private String[] indices;
	
	@NotEmpty
	@ApiModelProperty(value = "Filtros com campos e valores para realizar pesquisa", required = true)
	private Map<String, String> filtros;
	
	@ApiModelProperty(value = "Os campos a serem retornados", required = false)
	private String[] campos;
	
	@ApiModelProperty(value = "Campo para ordenar pesquisa", required = false)
	private String ordenador;
	
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
	 * @return the ordenador
	 */
	public String getOrdenador() {
		return ordenador;
	}

	/**
	 * @param ordenador the ordenador to set
	 */
	public void setOrdenador(String ordenador) {
		this.ordenador = ordenador;
	}
}
