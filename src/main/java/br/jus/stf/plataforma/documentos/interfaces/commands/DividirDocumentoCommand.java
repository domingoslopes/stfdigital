package br.jus.stf.plataforma.documentos.interfaces.commands;

import javax.validation.constraints.NotNull;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import br.jus.stf.shared.DocumentoId;

/**
 * Command para divisão de um documento
 * 
 * @author Tomas.Godoi
 *
 */
@ApiModel("Contém as informações necessárias para dividir um documento")
public class DividirDocumentoCommand {

	@ApiModelProperty("Id do documento a ser dividido")
	@NotNull
	private DocumentoId documento;
	@ApiModelProperty("Página inicial a ser dividida")
	@NotNull
	private Integer paginaInicial;
	@ApiModelProperty("Página final a ser dividida")
	@NotNull
	private Integer paginaFinal;

	public DividirDocumentoCommand(DocumentoId documento, Integer paginaInicial, Integer paginaFinal) {
		this.documento = documento;
		this.paginaInicial = paginaInicial;
		this.paginaFinal = paginaFinal;
	}
	
	public DocumentoId getDocumento() {
		return documento;
	}

	public void setDocumento(DocumentoId documento) {
		this.documento = documento;
	}

	public Integer getPaginaInicial() {
		return paginaInicial;
	}

	public void setPaginaInicial(Integer paginaInicial) {
		this.paginaInicial = paginaInicial;
	}

	public Integer getPaginaFinal() {
		return paginaFinal;
	}

	public void setPaginaFinal(Integer paginaFinal) {
		this.paginaFinal = paginaFinal;
	}
}
