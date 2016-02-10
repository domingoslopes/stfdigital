package br.jus.stf.plataforma.documentos.interfaces.commands;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import br.jus.stf.shared.DocumentoId;

@ApiModel("Contém as informações necessárias para dividir um documento")
public class DividirDocumentoCommand {

	@ApiModelProperty("Id do documento a ser dividido")
	private DocumentoId documento;
	@ApiModelProperty("Página inicial a ser dividida")
	private Integer paginaInicial;
	@ApiModelProperty("Página final a ser dividida")
	private Integer paginaFinal;

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
