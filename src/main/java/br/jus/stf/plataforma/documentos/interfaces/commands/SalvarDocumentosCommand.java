package br.jus.stf.plataforma.documentos.interfaces.commands;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import br.jus.stf.shared.DocumentoTemporarioId;

/**
 * Comando para persistir documentos temporários
 * 
 * @author Lucas.Rodrigues
 *
 */
public class SalvarDocumentosCommand {
	
	@NotEmpty
	private List<DocumentoTemporarioId> documentos;

	/**
	 * @return the documentos
	 */
	public List<DocumentoTemporarioId> getDocumentos() {
		return documentos;
	}

	/**
	 * @param documentos the documentos to set
	 */
	public void setDocumentos(List<DocumentoTemporarioId> documentos) {
		this.documentos = documentos;
	}

}
