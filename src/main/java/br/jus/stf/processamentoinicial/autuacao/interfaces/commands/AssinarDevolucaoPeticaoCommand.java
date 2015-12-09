package br.jus.stf.processamentoinicial.autuacao.interfaces.commands;

import javax.validation.constraints.NotNull;

import com.wordnik.swagger.annotations.ApiModelProperty;

public class AssinarDevolucaoPeticaoCommand {

	@NotNull
	@ApiModelProperty(value = "Id da petição.", required = true)
	private Long peticaoId;

	@ApiModelProperty(value = "Id do documento de devolução assinado.", required = false)
	private String documentoId;

	public Long getPeticaoId() {
		return peticaoId;
	}

	public void setPeticaoId(Long peticaoId) {
		this.peticaoId = peticaoId;
	}

	public String getDocumentoId() {
		return documentoId;
	}

	public void setDocumentoId(String documentoId) {
		this.documentoId = documentoId;
	}

}
