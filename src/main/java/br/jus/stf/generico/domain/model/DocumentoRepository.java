package br.jus.stf.generico.domain.model;

import java.util.List;

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
	public DocumentoId find(DocumentoId documentoId);

	public List<Documento> findAll();

	/**
	 * 
	 * @param documento
	 */
	public void store(Documento documento);

}