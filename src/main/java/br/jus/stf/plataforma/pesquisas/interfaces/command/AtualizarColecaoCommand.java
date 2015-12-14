package br.jus.stf.plataforma.pesquisas.interfaces.command;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Comando de atualização de um elemento específico de uma coleção.
 * 
 * @author Tomas.Godoi
 *
 */
@ApiModel("Comando de atualização de dados de um item específico de uma coleção")
public class AtualizarColecaoCommand extends AtualizarCommand {

	@ApiModelProperty(value = "O nome do campo que contém a coleção", required = true)
	@NotBlank
	private String campoColecao;
	
	@ApiModelProperty(value = "Expressão do identificador do item da coleção", required = true)
	@NotBlank
	private String expressaoId;
	
	@ApiModelProperty(value = "O id do item a ser atualizado", required = true)
	@NotEmpty
	private Object idItem;

	public String getCampoColecao() {
		return campoColecao;
	}

	public void setCampoColecao(String campoColecao) {
		this.campoColecao = campoColecao;
	}

	public String getExpressaoId() {
		return expressaoId;
	}

	public void setExpressaoId(String expressaoId) {
		this.expressaoId = expressaoId;
	}

	public Object getIdItem() {
		return idItem;
	}

	public void setIdItem(Object idItem) {
		this.idItem = idItem;
	}

}
