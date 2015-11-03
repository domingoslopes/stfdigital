package br.jus.stf.processamentoinicial.autuacao.domain;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

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

}
