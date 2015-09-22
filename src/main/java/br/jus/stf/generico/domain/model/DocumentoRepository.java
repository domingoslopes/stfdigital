package br.jus.stf.generico.domain.model;

import java.io.InputStream;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import br.jus.stf.shared.domain.model.DocumentoId;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:34:02
 */
@NoRepositoryBean
public interface DocumentoRepository extends Repository<Documento, DocumentoId> {
	
	/**
	 * Pesquisa um documento
	 * 
	 * @param documentoId
	 */
	public Documento findOne(DocumentoId documentoId);
	
	/**
	 * Pesquisa um documento
	 * 
	 * @param documentoId
	 * @return o stream do conteúdo
	 */
	public InputStream loadStream(DocumentoId documentoId);
	
	/**
	 * Salva um documento
	 * 
	 * @param documento
	 */
	public DocumentoId save(String documentoTemporario);

	/**
	 * Salva um documento temporário
	 * 
	 * @param documentoTemporario
	 * @return identificacao do temporário
	 */
	public String storeTemp(DocumentoTemporario documentoTemporario);
	
	public DocumentoId nextId();

}