package br.jus.stf.pesquisa.interfaces.command;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.databind.JsonNode;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author Lucas.Rodrigues
 *
 */
@ApiModel("Comando de criação de índice")
public class CriarIndiceCommand {
	
	@ApiModelProperty(value = "Índice a ser criado", required = true)
	@NotBlank
	private String indice;
	
	@ApiModelProperty(value = "Configuração do índice", required = true)
	@NotNull
	private JsonNode configuracao;

	public CriarIndiceCommand() {
		
	}
	
	public CriarIndiceCommand(final String indice, final JsonNode configuracao) {		
		this.indice = indice;
		this.configuracao = configuracao;
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
	 * @return the configuracao
	 */
	public JsonNode getConfiguracao() {
		return configuracao;
	}

	/**
	 * @param configuracao the configuracao to set
	 */
	public void setConfiguracao(JsonNode configuracao) {
		this.configuracao = configuracao;
	}
	
}
