package br.jus.stf.plataforma.documentos.infra.persistence;

import br.jus.stf.plataforma.documentos.domain.model.ConteudoDocumento;
import br.jus.stf.plataforma.documentos.domain.model.DocumentoTemporario;
import br.jus.stf.shared.DocumentoId;

/**
 * Repositório de conteúdo do documento.
 * 
 * @author Tomas.Godoi
 *
 */
public interface ConteudoDocumentoRepository {

	/**
	 * Faz o download do conteúdo especificado.
	 * 
	 * @param numeroConteudo
	 * @return
	 */
	ConteudoDocumento downloadConteudo(String numeroConteudo);

	/**
	 * Remove o conteúdo de um documento.
	 * 
	 * @param numeroConteudo Número do conteúdo
	 */
	void deleteConteudo(String numeroConteudo);

	/**
	 * Salva o conteúdo do documento temporário com o id especificado,
	 * retornando o número do conteúdo do documento.
	 * 
	 * @param documentoId Id do Documento
	 * @param documentoTemporario Documento Temporário cujo documento será armazenado
	 * @return Número do conteúdo do documento
	 */
	String save(DocumentoId documentoId, DocumentoTemporario documentoTemporario);

}
