package br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Objeto usado para enviar os dados das peças de um processo para o front-end.
 * 
 * @author Anderson.Araujo
 * @since 22.02.2016
 *
 */
@ApiModel(value = "Objeto usado para enviar os dados das peças de um processo para o front-end.")
public class PecaProcessoDto {
	@ApiModelProperty(value = "Id da peça.")
	private Long pecaId;
	
	@ApiModelProperty(value = "Id do documento.")
	private Long documentoId;
	
	@ApiModelProperty(value = "Tipo de peça.")
	private String tipo;
	
	@ApiModelProperty(value = "Descrição da peça.")
	private String descricao;
	
	@ApiModelProperty(value = "Nº de ordem da peça.")
	private Long numeroOrdem;
	
	@ApiModelProperty(value = "Visibilidade da peça.")
	private String visibilidade;
	
	@ApiModelProperty(value = "Situação da peça.")
	private String situacao;
	
	public PecaProcessoDto(Long pecaId, Long documentoId, String tipo, String descricao, Long numeroOrdem, String visibilidade, String situacao){
		this.pecaId = pecaId;
		this.documentoId = documentoId;
		this.tipo = tipo;
		this.descricao = descricao;
		this.numeroOrdem = numeroOrdem;
		this.visibilidade = visibilidade;
		this.situacao = situacao;
	}

	public Long getPecaId() {
		return pecaId;
	}

	public Long getDocumentoId() {
		return documentoId;
	}

	public String getTipo() {
		return tipo;
	}

	public String getDescricao() {
		return descricao;
	}

	public Long getNumeroOrdem() {
		return numeroOrdem;
	}

	public String getVisibilidade() {
		return visibilidade;
	}

	public String getSituacao() {
		return situacao;
	}
}
