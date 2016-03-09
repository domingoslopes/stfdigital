package br.jus.stf.processamentoinicial.suporte.interfaces.commands;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Comando para geração de textos da petição com substituição de tags.
 * 
 * @author Tomas.Godoi
 *
 */
@ApiModel("Comando para geração de texto")
public class GerarTextoPeticaoCommand {

	@ApiModelProperty("O id da petição")
	private Long peticaoId;

	@ApiModelProperty("O id do modelo de texto")
	private Long modeloId;

	@ApiModelProperty("As substituições das tags")
	private List<SubstituicaoTagTexto> substituicoes;

	public Long getPeticaoId() {
		return peticaoId;
	}

	public void setPeticaoId(Long peticaoId) {
		this.peticaoId = peticaoId;
	}

	public Long getModeloId() {
		return modeloId;
	}

	public void setModeloId(Long modeloId) {
		this.modeloId = modeloId;
	}

	public List<SubstituicaoTagTexto> getSubstituicoes() {
		return substituicoes;
	}

	public void setSubstituicoes(List<SubstituicaoTagTexto> substituicoes) {
		this.substituicoes = substituicoes;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
