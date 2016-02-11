package br.jus.stf.plataforma.documentos.interfaces.commands;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import br.jus.stf.shared.DocumentoId;

/**
 * Command para união de documentos.
 * 
 * @author Tomas.Godoi
 *
 */
@ApiModel("Contém as informações necessárias unir documentos")
public class UnirDocumentosCommand {

	@ApiModelProperty("Documentos a serem unidos")
	@NotEmpty
	private List<DocumentoId> documentos;

	public UnirDocumentosCommand(List<DocumentoId> documentos) {
		this.documentos = documentos;
	}

	public List<DocumentoId> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<DocumentoId> documentos) {
		this.documentos = documentos;
	}
}
