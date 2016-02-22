/**
 * 
 */
package br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.commands;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Comando de envio de peças processuais do front-end para o back-end.
 * 
 * @author Anderson.Araujo
 *
 * @since 17.02/2016
 */
@ApiModel(value="Comando de envio de peças processuais do front-end para o back-end.")
public class PecaProcessual {
	@ApiModelProperty(value = "Conjunto de peças processuais", required=false)
	private String documentoTemporarioId;
	
	@NotNull
	@ApiModelProperty(value = "ID do tipo de peça", required=true)
	private Long tipoPecaId;
	
	@NotEmpty
	@ApiModelProperty(value = "Visibilidade da peça", required=true)
	private String visibilidade;
	
	@NotEmpty
	@ApiModelProperty(value = "Situação da peça", required=true)
	private String situacao;
	
	@NotEmpty
	@ApiModelProperty(value = "Descrição da peça", required=true)
	private String descricao;
	
	@ApiModelProperty(value = "Página inicial da peça", required=false)
	private Integer paginaInicial;
	
	@ApiModelProperty(value = "Página inicial da peça", required=false)
	private Integer paginaFinal;
	
	public String getDocumentoTemporarioId(){
		return documentoTemporarioId;
	}
	
	public Long getTipoPecaId() {
		return tipoPecaId;
	}
	
	public String getVisibilidade(){
		return visibilidade;
	}
	
	public String getSituacao(){
		return situacao;
	}
	
	public String getDescricao(){
		return descricao;
	}
	
	public Integer getPaginaInicial(){
		return paginaInicial;
	}
	
	public Integer getPaginaFinal(){
		return paginaFinal;
	}
}
