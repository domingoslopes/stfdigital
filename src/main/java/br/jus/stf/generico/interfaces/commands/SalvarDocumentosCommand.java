package br.jus.stf.generico.interfaces.commands;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Comando para persistir documentos temporários
 * 
 * @author Lucas.Rodrigues
 *
 */
public class SalvarDocumentosCommand {
	
	@NotEmpty
	private List<String> documentos;

	/**
	 * @return the documentos
	 */
	public List<String> getDocumentos() {
		return documentos;
	}

	/**
	 * @param documentos the documentos to set
	 */
	public void setDocumentos(List<String> documentos) {
		this.documentos = documentos;
	}

}
