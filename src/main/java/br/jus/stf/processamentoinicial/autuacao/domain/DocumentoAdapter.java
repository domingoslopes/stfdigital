package br.jus.stf.processamentoinicial.autuacao.domain;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.Range;
import org.springframework.stereotype.Component;

import br.jus.stf.processamentoinicial.suporte.interfaces.commands.SubstituicaoTagTexto;
import br.jus.stf.shared.DocumentoId;
import br.jus.stf.shared.DocumentoTemporarioId;

/**
 * @author Lucas Rodrigues
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 25.09.2015
 */
@Component
public interface DocumentoAdapter {

	/**
	 * Salva os documentos e recupera os ids na ordem de envio
	 * 
	 * @param documentosTemporarios
	 * @return a lista de ids dos documentos
	 */
	Set<DocumentoId> salvar(List<DocumentoTemporarioId> documentosTemporarios);
	
	DocumentoId salvar(DocumentoTemporarioId documentoTemporario);
	
	DocumentoTemporarioId upload(String nome, byte[] documento);

	List<DocumentoId> dividirDocumento(DocumentoId documento, List<Range<Integer>> intervalosDivisao);
	
	DocumentoId unirDocumentos(List<DocumentoId> documentos);

	/**
	 * Gera o documento final a partir de um documento editável.
	 * 
	 * @param documentoId
	 * @return
	 */
	DocumentoId gerarDocumentoFinal(DocumentoId documentoId);
	
	/**
	 * Gera um documento e substitui suas tags pelos respectivos valores.
	 * @param documento Id do documento.
	 * @param substituicoes Lista contendo as tags e seus respectivos valores
	 * @return Id do documento gerado.
	 */
	DocumentoId gerarDocumentoComTags(DocumentoId documento, List<SubstituicaoTagTexto> substituicoes);
	
}
