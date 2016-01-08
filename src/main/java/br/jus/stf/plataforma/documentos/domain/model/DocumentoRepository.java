package br.jus.stf.plataforma.documentos.domain.model;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import br.jus.stf.shared.DocumentoId;
import br.jus.stf.shared.DocumentoTemporarioId;

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
	public ConteudoDocumentoDownload download(DocumentoId documentoId);
	
	/**
	 * Salva um documento
	 * 
	 * @param documento
	 */
	public DocumentoId save(DocumentoTemporarioId documentoTemporario);
	
	/**
	 * Exclui um documento
	 * 
	 * @param documento
	 */
	public void delete(Documento documento);

	/**
	 * Salva um documento temporário
	 * 
	 * @param documentoTemporario
	 * @return identificacao do temporário
	 */
	public String storeTemp(DocumentoTemporario documentoTemporario);
	
	/**
	 * Remove um arquivo temporário
	 * 
	 * @param tempId
	 * @return
	 */
	public void removeTemp(String tempId);
	
	/**
	 * Recupera o próximo id do documento
	 * 
	 * @return
	 */
	public DocumentoId nextId();

}