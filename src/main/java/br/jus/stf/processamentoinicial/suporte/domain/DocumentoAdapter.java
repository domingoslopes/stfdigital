package br.jus.stf.processamentoinicial.suporte.domain;

import java.io.InputStream;
import java.util.List;

import br.jus.stf.processamentoinicial.suporte.interfaces.commands.SubstituicaoTagTexto;
import br.jus.stf.shared.DocumentoId;
import br.jus.stf.shared.DocumentoTemporarioId;

/**
 * Adaptador para o contexto de documentos.
 * 
 * @author Tomas.Godoi
 *
 */
public interface DocumentoAdapter {

	/**
	 * Recupera o conteúdo de um documento.
	 * 
	 * @param documentoId
	 * @return
	 */
	InputStream recuperarConteudo(DocumentoId documentoId);

	DocumentoId salvar(DocumentoTemporarioId documentoTemporario);

	DocumentoTemporarioId upload(String nome, byte[] documento);

	DocumentoId gerarDocumentoComTags(DocumentoId documento, List<SubstituicaoTagTexto> substituicoes);

}
