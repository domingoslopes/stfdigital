/**
 * 
 */
package br.jus.stf.processamentoinicial.autuacao.interfaces.commands;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import br.jus.stf.processamentoinicial.suporte.interfaces.commands.SubstituicaoTagTexto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Comando usado para gerar o texto do documento de devolução de uma petição.
 * 
 * @author Anderson.Araujo
 * @since 15.03.2016
 *
 */
@ApiModel("Comando usado para gerar o texto do documento de devolução de uma petição.")
public class GerarTextoDevolucaoPeticaoCommand {
	@ApiModelProperty("Id da petição")
	private Long peticaoId;

	@ApiModelProperty("Id do modelo de documento.")
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
