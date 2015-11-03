package br.jus.stf.plataforma.pesquisas.interfaces.command;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.databind.JsonNode;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Comando para indexar objetos. Deve seguir a notação do Elasticsearch de mapeamentos
 * 
 * @author Lucas.Rodrigues
 *
 */
@ApiModel("Comando de indexação de objetos")
public class IndexarCommand {
	
	@ApiModelProperty(value = "Id do objeto")
	private String id;
	
	@ApiModelProperty(value = "Tipo do objeto", required = true)
	@NotBlank
	private String tipo;
	
	@ApiModelProperty(value = "Índice para indexar", required = true)
	@NotBlank
	private String indice;
	
	@ApiModelProperty(value = "Objeto a ser indexado", required = true)
	@NotNull
	private JsonNode objeto;
	
	public IndexarCommand() {
		
	}
	
	public IndexarCommand(final String id, final String tipo, final String indice, final JsonNode objeto) {		
		this.id = id;
		this.tipo = tipo;
		this.indice = indice;
		this.objeto = objeto;
	}
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the indice
	 */
	public String getIndice() {
		return indice;
	}

	/**
	 * @param indice the indice to set
	 */
	public void setIndice(String indice) {
		this.indice = indice;
	}

	/**
	 * @return the objeto
	 */
	public JsonNode getObjeto() {
		return objeto;
	}

	/**
	 * @param objeto the objeto to set
	 */
	public void setObjeto(JsonNode objeto) {
		this.objeto = objeto;
	}
	
}
