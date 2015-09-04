package br.jus.stf.generico.domain.model;

import br.jus.stf.shared.domain.model.DocumentoId;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:34:02
 */
public interface DocumentoRepository {

	/**
	 * 
	 * @param documentoId
	 */
	public Documento find(DocumentoId documentoId);
	
	/**
	 * 
	 * @param documento
	 */
	public void store(Documento documento);

}